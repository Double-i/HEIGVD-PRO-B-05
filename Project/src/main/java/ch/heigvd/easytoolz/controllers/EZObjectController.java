package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.EZObjectView;
import ch.heigvd.easytoolz.models.Localisation;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import ch.heigvd.easytoolz.repositories.LocalisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/objects")
public class EZObjectController {

    @Autowired
    EZObjectRepository ezObjectRepository;

    @Autowired
    LocalisationRepository localisationRepository;

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
     * url: api/objects/find/?username=value
     * @param username
     * @return
     */
    @GetMapping("/find/byUser/{username}")
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
    @PostMapping("/addObject")
    public EZObject addObject(@RequestBody EZObject newObject)
    {
        EZObject obj = new EZObject(newObject.getName(),newObject.getDescription(),newObject.getOwner(),newObject.getLocalisation());
        return ezObjectRepository.save(obj);
    }

    /**
     * Find an object from the database
     * @param objectname
     * @return
     */
    @GetMapping("find/object/{objectname}")
    @ResponseBody
    public List<EZObject> getObejctByName(@PathVariable String objectname)
    {
        return ezObjectRepository.findByNameContaining(objectname);
    }

    /**
     * Find objects from description content
     * @param content
     * @return
     */
    @GetMapping("find/description/{content}")
    @ResponseBody
    public List<EZObject> getObjectbyDescription(@PathVariable String content)
    {
        return ezObjectRepository.findByDescriptionContaining(content);
    }

    Localisation getLocalisationID(float latitude, float longitude)
    {
        return localisationRepository.findByLatitudeAndLongitude(latitude,longitude);
    }

    @GetMapping("find/localisation/{latitude}/{longitude}")
    @ResponseBody
    EZObject findByLocalisation(@PathVariable float latitude,@PathVariable float longitude)
    {
        Localisation id = localisationRepository.findByLatitudeAndLongitude(latitude,longitude);

        return ezObjectRepository.findByLocalisation(id.getId());
    }

    @GetMapping("find/{latitude}/{longitude}")
    @ResponseBody
    Localisation findlocation(@PathVariable float latitude,@PathVariable float longitude)
    {
        Localisation id = localisationRepository.findByLatitudeAndLongitude(latitude,longitude);
        return id;
    }


}
