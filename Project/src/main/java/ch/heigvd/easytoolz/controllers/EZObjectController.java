package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.services.interfaces.EZObjectService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/objects")
public class EZObjectController {


    @Autowired
    UserService userService;

    @Autowired
    EZObjectService ezObjectService;

    @GetMapping
    public List<EZObject> index()
    {
        return ezObjectService.get();
    }

    @GetMapping("find/{id}")
    public EZObjectView get(@PathVariable int id)
    {
        return ezObjectService.getObject(id);
    }


    @GetMapping("/find/user/{username}")
    @ResponseBody
    public List<EZObjectView> getByOwner(@PathVariable String username)
    {
        return ezObjectService.getObjectByOwner(username);
    }


    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody EZObject newObject)
    {
        ezObjectService.addObject(newObject);

        return new ResponseEntity<>("Object has been savec", HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody EZObject o)
    {
        ezObjectService.updateObject(o);
        return new ResponseEntity<>("Object has been updated",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id)
    {
        ezObjectService.deleteObject(id);
        return new ResponseEntity<>("Object has been deleted",HttpStatus.OK);
    }


    @GetMapping("find/name/{objectName}")
    @ResponseBody
    public List<EZObjectView> getByName(@PathVariable String objectName)
    {
        return ezObjectService.getObjectByName(objectName);
    }


    @GetMapping("find/description/{content}")
    @ResponseBody
    public List<EZObjectView> getByDescription(@PathVariable String content)
    {
        return ezObjectService.getObjectByDescription(content);
    }


    @GetMapping("find/localisation")
    @ResponseBody
    public List<EZObjectView> getByLocalisation(@RequestParam(name="Latitude") BigDecimal lat, @RequestParam(name="Longitude") BigDecimal lng)
    {
        return ezObjectService.getObjectsByLocalisation(lat,lng);
    }

    @GetMapping("find/tags")
    @ResponseBody
    public List<EZObject> getByTags(@RequestBody List<Tag> tags)
    {
        return ezObjectService.getObjectsByTag(tags);
    }

}
