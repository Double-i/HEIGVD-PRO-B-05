package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.repositories.EZObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/objects")
public class EZObjectController {

    @Autowired
    EZObjectRepository EZObjectRepository;

    @GetMapping
    public List<EZObject> index()
    {
        return EZObjectRepository.findAll();
    }

    /**
     * Find objects by owner
     * url: api/objects/find/?username=value
     * @param username
     * @return
     */
    @GetMapping("/find")
    @ResponseBody
    public List<EZObject> getObjectByOwner(@RequestParam(name="username") String username)
    {
        return EZObjectRepository.findByOwner(username);
    }

    
    @GetMapping("{tag_id}")
    @ResponseBody
    public List<EZObject> getObjectByTag(@PathVariable int tag_id)
    {
        return EZObjectRepository.findByTagID(tag_id);
    }


    @PostMapping("/addObject")
    public EZObject addObject(@RequestBody EZObject newObject)
    {
        EZObject obj = new EZObject(20,newObject.getName(),newObject.getDescription(),newObject.getOwner(),newObject.getTagID());
        return EZObjectRepository.save(obj);
    }

}
