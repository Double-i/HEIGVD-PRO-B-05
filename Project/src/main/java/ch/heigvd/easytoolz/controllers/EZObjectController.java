package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.EZObjectImage;
import ch.heigvd.easytoolz.services.interfaces.EZObjectService;
import ch.heigvd.easytoolz.services.interfaces.StorageService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.models.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/objects")
public class EZObjectController {


    @Autowired
    UserService userService;

    @Autowired
    EZObjectService ezObjectService;




    @Secured("ROLE_TEST")
    @GetMapping
    public List<EZObjectView> index()
    {
        return ezObjectService.getAll();
    }

    @GetMapping("/{id}")
    public EZObjectView get(@PathVariable int id)
    {
        return ezObjectService.getObject(id);
    }


    @GetMapping("/owner/{username}")
    @ResponseBody
    public List<EZObjectView> getByOwner(@PathVariable String username)
    {
        return ezObjectService.getObjectByOwner(username);
    }
    
    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value="/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> add(@RequestParam(name="object") String newObject,
                                      @RequestParam(name="image")List<MultipartFile> files) throws Exception {


        EZObject obj = mapper.readValue(newObject,EZObject.class);
        ezObjectService.addObject(obj, files);
        return new ResponseEntity<>("Object has been saved", HttpStatus.OK);
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
    public List<EZObjectView> getByTags(@RequestBody List<Tag> tags)
    {
        return ezObjectService.getObjectsByTag(tags);
    }

    @GetMapping("filter")
    public List<EZObject> findFiltered(
            @RequestParam(name="names",required = false) List<String> names,
            @RequestParam(name="owners",required = false) List<String> owners,
            @RequestParam(name="description",required = false) List<String> description,
            @RequestParam(name="tags", required  = false) List<Tag> tags)

    {

        return ezObjectService.getFiltered(names,owners,description,tags);
    }

    @GetMapping("/images/{id}")
    @ResponseBody
    public List<EZObjectImage> getObjecImagePath(@PathVariable int id)
    {
        return ezObjectService.getObjectImages(id);
    }

}
