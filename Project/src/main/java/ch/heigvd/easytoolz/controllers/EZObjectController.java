package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.controllers.exceptions.EZObjectNotFoundException;
import ch.heigvd.easytoolz.controllers.exceptions.UserNotFoundException;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.models.Tag;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.repositories.EZObjectViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/objects")
public class EZObjectController {

    @Autowired
    EZObjectRepository ezObjectRepository;

    @Autowired
    EZObjectViewRepository objectViewRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Get the list of all the objects
     * @return
     */
    @GetMapping
    public List<EZObject> index()
    {
        return ezObjectRepository.findAll();
    }


    /**
     * Find objects by owner
     * url: api/objects/find/{username}
     * @param username
     * @return
     */
    @GetMapping("/find/user/{username}")
    @ResponseBody
    public List<EZObjectView> getObjectByOwner(@PathVariable String username)
    {

        if(objectViewRepository.findByObjectOwner(username).size() == 0)
            throw new EZObjectNotFoundException("No Objects where found for user "+username);
        return objectViewRepository.findByObjectOwner(username);
    }

    /**
     * Add an object into the database
     * @param newObject
     * @return
     */
    @PostMapping("/add")
    public EZObject addObject(@RequestBody EZObject newObject)
    {
        User owner = userRepository.findByUserName(newObject.getOwnerUserName());
        newObject.setOwner(owner);
        return ezObjectRepository.save(newObject);
    }

    /**
     * Updates an object into the database
     * @param o
     * @return
     */
    @PostMapping("/update")
    public EZObject updateObject(@RequestBody EZObject o)
    {
        EZObject updated = ezObjectRepository.findByID(o.getID());
        if(updated == null)
            throw new EZObjectNotFoundException("Object not found " + o.getID() + " ");
        
        updated.setDescription(o.getDescription());
        updated.setName(o.getName());
        updated.setImages(o.getImages());
        updated.setObjecttags(o.getObjecttags());


        return ezObjectRepository.save(updated);
    }

    /**
     * Find an object from the database
     * @param objectName
     * @return
     */
    @GetMapping("find/name/{objectName}")
    @ResponseBody
    public List<EZObject> getObjectByName(@PathVariable String objectName)
    {
        return ezObjectRepository.findByNameContaining(objectName);
    }

    /**
     * Find objects via description content
     * @param content
     * @return
     */
    @GetMapping("find/description/{content}")
    @ResponseBody
    public List<EZObject> getObjectByDescription(@PathVariable String content)
    {
        return ezObjectRepository.findByDescriptionContaining(content);
    }


    @GetMapping("find/localisation")
    @ResponseBody
    public List<EZObject> getObjectsByLocalisation(@RequestParam(name="Latitude") BigDecimal lat, @RequestParam(name="Longitude") BigDecimal lng)
    {
        return ezObjectRepository.findByOwner_Address_LatAndOwner_Address_Lng(lat,lng);
    }

    @GetMapping("find/tags")
    @ResponseBody
    public List<EZObject> getObjectsByTag(@RequestBody List<Tag> tags)
    {
        return ezObjectRepository.findByobjecttagsIn(tags);
    }

}
