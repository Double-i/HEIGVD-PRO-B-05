package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.models.*;
import ch.heigvd.easytoolz.repositories.EzObjectImageRepository;
import ch.heigvd.easytoolz.services.interfaces.*;
import ch.heigvd.easytoolz.util.ServiceUtils;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class EZObjectServiceImpl implements EZObjectService {

    @Autowired
    EZObjectRepository ezObjectRepository;

    @Autowired
    EzObjectImageRepository imagesRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    UserService userService;

    @Autowired
    LoanService loanService;

    @Autowired
    AuthenticationService authenticationService;

    @PersistenceContext
    EntityManager entityManager;

    public Predicate buildPredicates(List<String> namesList,
                                     List<String> ownersList,
                                     List<String> descriptionList,
                                     List<Tag>   tagList,
                                     CriteriaBuilder cb ,
                                     Root<EZObject> root)
    {

        List<Predicate> predicates = new LinkedList<>();
        List<Predicate> tagPredicates = new LinkedList<>();

        Predicate finalQuery;
        Predicate queries = cb.conjunction();
        Predicate tagQuery =  cb.conjunction();

        User currentUser = authenticationService.getTheDetailsOfCurrentUser();

        if(currentUser != null){
            predicates.add(cb.notEqual(root.get(EZObject_.OWNER).get("userName"), currentUser.getUserName()));
        }

        if(namesList != null) {
            for(String s : namesList) {
                predicates.add( cb.like(root.get(EZObject_.NAME), ServiceUtils.transformLike(s)));
            }
        }

        if(ownersList !=null) {
            for(String s : ownersList) {
                predicates.add( cb.equal(root.get(EZObject_.OWNER).get("userName"),s));
            }
        }
        if(descriptionList != null) {
            for(String s : descriptionList) {
                predicates.add( cb.like(root.get(EZObject_.DESCRIPTION),ServiceUtils.transformLike(s)));
            }
        }
        Join<Tag,EZObject> objectJoin = root.join(EZObject_.OBJECT_TAGS);
        if(tagList != null && tagList.size() > 0) {
            for(Tag t : tagList) {
                tagPredicates.add( cb.equal(objectJoin.get("name").as(String.class),t.getName()));
            }
        }

        if(predicates.size() > 0)
            queries =  cb.and(predicates.toArray(new Predicate[0]));
        if(tagPredicates.size() > 0)
            tagQuery =  cb.or(tagPredicates.toArray(new Predicate[0]));

        finalQuery =  cb.and(queries,tagQuery);

        return finalQuery;
    }

    public int getFilteredCount( List<String> namesList,
                                       List<String> ownersList,
                                       List<String> descriptionList,
                                       List<Tag>   tagList,int  page)
    {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<EZObject> countRoot = countQuery.from(EZObject.class);
        countQuery.select(cb.count(countRoot)).where(buildPredicates(namesList,ownersList,descriptionList,tagList,cb,countRoot));
        Long count =  entityManager.createQuery(countQuery).getSingleResult();

        return count.intValue();
    }

    public List<EZObject> getFiltered( List<String> namesList,
                                           List<String> ownersList,
                                           List<String> descriptionList,
                                       List<Tag>   tagList,int  page)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        List<EZObject> objects;

        CriteriaQuery<EZObject> query = cb.createQuery(EZObject.class);
        Root<EZObject> root = query.from(EZObject.class);

        query.where(buildPredicates(namesList,ownersList,descriptionList,tagList,cb,root)).distinct(true);

        objects = entityManager.createQuery(query).setFirstResult(page*10).setMaxResults(10).getResultList();

        return objects;
    }
    public boolean exists(EZObject obj) {
        return obj.isActive();
    }

    public EZObjectView getObject(int id) {
        EZObjectView obj = ezObjectRepository.getEZObjectByID(id);
        if (obj == null)
            throw new EZObjectNotFoundException("No Objects where found for user " + id);
        return obj;
    }

    public List<EZObjectView> getAll(int page, int pageLength) {
        Pageable pageable =  PageRequest.of(page,pageLength);
        return ezObjectRepository.getAllByIsActive(true, pageable).getContent();
    }

    public List<EZObjectView> getObjectByOwner(String username) {
        List<EZObjectView> res = ezObjectRepository.getByOwner_UserName(username);

        if (res.size() == 0)
            throw new EZObjectNotFoundException("No Objects where found for user " + username);

        return res;
    }

    public void addObject(EZObject newObject, List<MultipartFile> files) throws Exception {

        User owner = authenticationService.getTheDetailsOfCurrentUser();

        newObject.setOwner(owner);

        ezObjectRepository.save(newObject);

        if(files != null)
        {
            List<EZObjectImage>images = new ArrayList<>();
            for (MultipartFile file : files) {
                EZObjectImage img_path = new EZObjectImage();
                img_path.setObject(newObject);

                storageService.store(file, newObject, img_path);
                images.add(img_path);
                imagesRepository.save(img_path);
            }
        }


    }

    public void updateObject(EZObject o, List<MultipartFile> files) throws Exception
    {
        EZObject updated = ezObjectRepository.findByID(o.getID());
        if (updated == null)
            throw new EZObjectNotFoundException("" + o.getID());

        updated.setDescription(o.getDescription());
        updated.setName(o.getName());
        updated.setImages(o.getImages());
        updated.setObjectTags(o.getObjectTags());

        if(files != null)
        {
            List<EZObjectImage>images = updated.getImages();
            if(images == null)
                images = new ArrayList<>();

            for (MultipartFile file : files) {
                EZObjectImage img_path = new EZObjectImage();
                img_path.setObject(updated);
                storageService.store(file, updated, img_path);
                images.add(img_path);
                imagesRepository.save(img_path);
            }
        }


        ezObjectRepository.save(updated);
    }

    public void deleteObject(int id) throws Exception {
        EZObject toDelete = ezObjectRepository.findByID(id);

        if (toDelete == null)
            throw new EZObjectNotFoundException("" + id);

        if(loanService.isObjectIsCurrentlyBorrowed(id)){
            throw new RuntimeException("ta maman");
        }

        toDelete.setActive(false);
        updateObject(toDelete,null);

    }

    public List<EZObjectImage> getObjectImages(int id)
    {
        return imagesRepository.findByEzObject_ID(id);
    }

    public List<EZObjectView> getObjectByName(String objectName) {
        return ezObjectRepository.getAllByNameContaining(objectName);
    }


    public List<EZObjectView> getObjectByDescription(String content) {
        return ezObjectRepository.getAllByDescriptionContaining(content);
    }


    public List<EZObjectView> getObjectsByLocalisation(BigDecimal lat, BigDecimal lng) {
        return ezObjectRepository.getAllByOwner_Address_LatAndOwner_Address_Lng(lat, lng);
    }

    public List<EZObjectView> getObjectsByTag(List<Tag> tags) {
        return ezObjectRepository.getAllByObjectTagsIn(tags);
    }

    public void deleteImage(int id) throws Exception
    {
        EZObjectImage toDelete = imagesRepository.findByID(id);
        if (toDelete == null)
            throw new EZObjectNotFoundException("" + id);

        toDelete.setIs_active(false);
        imagesRepository.save(toDelete);
    }



    public int getNbObjects()
    {
        return ezObjectRepository.countAllByIsActive(true);
    }

    public List<EZObjectView> getReportedObject(){
        return ezObjectRepository.getAllByIDIn(ezObjectRepository.getReportedObject());
    }

}
