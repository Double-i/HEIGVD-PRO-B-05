package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.models.*;
import ch.heigvd.easytoolz.repositories.EzObjectImageRepository;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.EZObjectService;
import ch.heigvd.easytoolz.services.interfaces.StorageService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import ch.heigvd.easytoolz.util.ServiceUtils;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ch.heigvd.easytoolz.models.EZObject_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    AuthenticationService authenticationService;

    @PersistenceContext
    EntityManager entityManager;

    public List<EZObject> getFiltered( List<String> namesList,
                                           List<String> ownersList,
                                           List<String> descriptionList,
                                       List<Tag>   tagList)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        List<EZObject> objects;

        CriteriaQuery<EZObject> query = criteriaBuilder.createQuery(EZObject.class);
        Root<EZObject> root = query.from(EZObject.class);

        List<Predicate> predicates = new LinkedList<>();
        List<Predicate> tagPredicates = new LinkedList<>();

        Predicate finalQuery;
        Predicate queries = criteriaBuilder.conjunction();
        Predicate tagQuery = criteriaBuilder.conjunction();
        if(namesList != null) {
            for(String s : namesList) {
                predicates.add(criteriaBuilder.like(root.get(EZObject_.NAME), ServiceUtils.transformLike(s)));
            }
        }

        if(ownersList !=null) {
            for(String s : ownersList) {
                predicates.add(criteriaBuilder.equal(root.get(EZObject_.OWNER).get("userName"),s));
            }

        }
        if(descriptionList != null) {
            for(String s : descriptionList) {
                predicates.add(criteriaBuilder.like(root.get(EZObject_.DESCRIPTION),ServiceUtils.transformLike(s)));
            }
        }
        Join<Tag,EZObject> objectJoin = root.join(EZObject_.OBJECT_TAGS,JoinType.INNER);
        if(tagList != null && tagList.size() > 0) {
            for(Tag t : tagList) {
                tagPredicates.add(criteriaBuilder.equal(objectJoin.get("name").as(String.class),t.getName()));
            }
        }

        if(predicates.size() > 0)
            queries = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        if(tagPredicates.size() > 0)
            tagQuery = criteriaBuilder.or(tagPredicates.toArray(new Predicate[0]));

        finalQuery = criteriaBuilder.and(queries,tagQuery);

        query.where(finalQuery).distinct(true);

        objects = entityManager.createQuery(query).getResultList();
        
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

    public List<EZObjectView> getAll() {
        return ezObjectRepository.getAllByIsActive(true);
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
            for(int i = 0; i < files.size(); i++)
            {
                EZObjectImage  img_path = new EZObjectImage();
                img_path.setObject(newObject);

                storageService.store(files.get(i),newObject, img_path);
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

            for(int i = 0; i < files.size(); i++)
            {
                EZObjectImage  img_path = new EZObjectImage();
                img_path.setObject(updated);
                storageService.store(files.get(i),updated, img_path);
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

    public List<EZObjectView> getReportedObject(){
        return ezObjectRepository.getAllByIDIn(ezObjectRepository.getReportedObject());
    }

}
