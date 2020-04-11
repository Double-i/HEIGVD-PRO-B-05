package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.controllers.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.controllers.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.services.EZObjectService;
import ch.heigvd.easytoolz.services.UserService;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.models.Tag;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.repositories.EZObjectViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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


    public List<EZObjectView> getAll()
    {
        return objectViewRepository.findAll();
    }


    public List<EZObjectView> getObjectByOwner( String username)
    {

        if(objectViewRepository.findByObjectOwner(username).size() == 0)
            throw new EZObjectNotFoundException("No Objects where found for user "+username);
        return objectViewRepository.findByObjectOwner(username);
    }

    public EZObjectView getObject(int id)
    {
        EZObjectView obj = objectViewRepository.findByObjectId(id);
        if(obj== null)
            throw new EZObjectNotFoundException("No Objects where found for user "+ id);
        return obj;
    }

    public void addObject( EZObject newObject)
    {
        User owner = userService.getUser(newObject.getOwnerUserName());
        newObject.setOwner(owner);
        ezObjectRepository.save(newObject);
    }


    public void updateObject( EZObject o)
    {
        EZObject updated = ezObjectRepository.findByID(o.getID());
        if(updated == null)
            throw new EZObjectNotFoundException( ""+o.getID() );

        updated.setDescription(o.getDescription());
        updated.setName(o.getName());
        updated.setImages(o.getImages());
        updated.setObjecttags(o.getObjecttags());


        ezObjectRepository.save(updated);
    }

    public void deleteObject(int id)
    {
        EZObject toDelete = ezObjectRepository.findByID(id);
        if(toDelete == null)
            throw new EZObjectNotFoundException(""+id);
        ezObjectRepository.delete(toDelete);
    }


    public List<EZObjectView> getObjectByName( String objectName)
    {
        return objectViewRepository.findByObjectNameContaining(objectName);
    }


    public List<EZObjectView> getObjectByDescription( String content)
    {
        return objectViewRepository.findByObjectDescriptionContaining(content);
    }


    public List<EZObjectView> getObjectsByLocalisation(BigDecimal lat,  BigDecimal lng)
    {
        return objectViewRepository.findByOwnerLatAndOwnerLng(lat,lng);
    }

    public List<EZObject> getObjectsByTag( List<Tag> tags)
    {
        return ezObjectRepository.findByObjectTagsIn(tags);
    }

}
