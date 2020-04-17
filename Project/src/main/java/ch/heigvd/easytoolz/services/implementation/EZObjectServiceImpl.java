package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.services.interfaces.EZObjectService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.models.Tag;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    UserService userService;

    @PersistenceContext
    EntityManager entityManager;


    private String like(String s)
    {
        return "%"+"%";
    }
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

        Predicate finalQuery = criteriaBuilder.disjunction();

        if(namesList != null) {
            for(String s : namesList) {
                predicates.add(criteriaBuilder.like(root.get("name"),"%"+s+"%"));
            }
        }

        if(ownersList !=null) {
            for(String s : ownersList) {
                predicates.add(criteriaBuilder.equal(root.get("owner").get("userName"),s));
            }

        }
        if(descriptionList != null) {
            for(String s : descriptionList) {
                predicates.add(criteriaBuilder.like(root.get("description"),"%"+s+"%"));
            }
        }
        Join<Tag,EZObject> objectJoin = root.join("objectTags",JoinType.INNER);
        if(tagList != null && tagList.size() > 0) {
            for(Tag t : tagList) {
                predicates.add(criteriaBuilder.equal(objectJoin.get("name").as(String.class),t.getName()));
            }
        }


        finalQuery = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
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

    public void addObject(EZObject newObject) {
        User owner = userService.getUser(newObject.getOwnerUserName());
        newObject.setOwner(owner);

        ezObjectRepository.save(newObject);
    }


    public void updateObject(EZObject o) {
        EZObject updated = ezObjectRepository.findByID(o.getID());
        if (updated == null)
            throw new EZObjectNotFoundException("" + o.getID());

        updated.setDescription(o.getDescription());
        updated.setName(o.getName());
        updated.setImages(o.getImages());
        updated.setObjectTags(o.getObjectTags());


        ezObjectRepository.save(updated);
    }

    public void deleteObject(int id) {
        EZObject toDelete = ezObjectRepository.findByID(id);
        if (toDelete == null)
            throw new EZObjectNotFoundException("" + id);

        toDelete.setActive(false);
        updateObject(toDelete);

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

}
