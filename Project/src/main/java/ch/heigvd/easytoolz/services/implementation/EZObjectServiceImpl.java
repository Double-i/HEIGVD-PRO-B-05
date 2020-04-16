package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.services.interfaces.EZObjectService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.models.Tag;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.repositories.EZObjectViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EZObjectServiceImpl implements EZObjectService {

    @Autowired
    EZObjectRepository ezObjectRepository;

    @Autowired
    EZObjectViewRepository objectViewRepository;

    @Autowired
    UserService userService;

    public boolean exists(EZObject obj) {
        return obj.isActive();
    }

    public List<EZObject> get() {
        return ezObjectRepository.findAll();
    }

    public EZObjectView getObject(int id) {
        EZObjectView obj = objectViewRepository.findByObjectId(id);
        if (obj == null || !exists(obj.getEzObject()))
            throw new EZObjectNotFoundException("No Objects where found for user " + id);
        return obj;
    }

    public List<EZObjectView> getAll() {
        return objectViewRepository.findByOrderByObjectIdAsc();
    }

    public List<EZObjectView> getObjectByOwner(String username) {
        List<EZObjectView> res = objectViewRepository.findByObjectOwner(username);

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
        return objectViewRepository.findByObjectNameContaining(objectName);
    }


    public List<EZObjectView> getObjectByDescription(String content) {
        return objectViewRepository.findByObjectDescriptionContaining(content);
    }


    public List<EZObjectView> getObjectsByLocalisation(BigDecimal lat, BigDecimal lng) {
        return objectViewRepository.findByOwnerLatitudeAndOwnerLongitude(lat, lng);
    }

    public List<EZObject> getObjectsByTag(List<Tag> tags) {
        return ezObjectRepository.findByObjectTagsIn(tags);
    }

}
