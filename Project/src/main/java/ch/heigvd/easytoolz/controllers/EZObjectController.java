package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.controllers.exceptions.EZObjectNotFoundException;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@RestController
@RequestMapping("/objects")
public class EZObjectController {

    @Autowired
    EZObjectRepository ezObjectRepository;

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
    public List<EZObject> getObjectByOwner(@PathVariable String username)
    {
        return ezObjectRepository.findByOwner_UserName(username);
    }

    /**
     * Add an object into the database
     * @param newObject
     * @return
     */
    @PostMapping("/add")
    public EZObject addObject(@RequestBody EZObject newObject)
    {
        EZObject obj = new EZObject(newObject.getName(),newObject.getDescription(),newObject.getOwner(),newObject.getObjecttags(),newObject.getImages());
        return ezObjectRepository.save(obj);
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
            throw new EntityNotFoundException("Object not found " + o.getID() + " ");
        
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


    /**@GetMapping("find/localisation/{latitude}/{longitude}")
    @ResponseBody


    @GetMapping("find/{latitude}/{longitude}")
    @ResponseBody*/



}
