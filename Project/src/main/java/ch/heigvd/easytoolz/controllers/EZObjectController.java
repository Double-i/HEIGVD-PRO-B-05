package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.exceptions.authentication.AccessDeniedException;
import ch.heigvd.easytoolz.models.*;
import ch.heigvd.easytoolz.models.json.SuccessResponse;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.EZObjectService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import ch.heigvd.easytoolz.views.AddressView;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.views.UserView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/objects")
public class EZObjectController {


    @Autowired
    private UserService userService;

    @Autowired
    private EZObjectService ezObjectService;

    @Autowired
    private AuthenticationService authenticationService;

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * @param nbItems nb.items wanted
     * @param pageno number of the page
     * @return all objects
     */
    @GetMapping
    public List<EZObjectView> index( @RequestParam(value = "nbItems", defaultValue = "10") int nbItems, @RequestParam(name = "page", defaultValue="0") int pageno)
    {
        List<EZObject> objects = ezObjectService.getFiltered(null,null,null,null, pageno);
        return objects.stream().map(this::convertToView).collect(Collectors.toList());
    }

    /**
     * @return nb. total of the objects in database
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> getNbObjects()
    {
        return ResponseEntity.ok(ezObjectService.getFilteredCount(null,null,null,null, 0));
    }

    /**
     * @param id
     * @return the object with the id "id"
     */
    @GetMapping("/{id}")
    public EZObjectView get(@PathVariable int id )
    {
        return ezObjectService.getObject(id);
    }


    /**
     * @param pageno number of the page
     * @return the object of the current user
     */
    @GetMapping("/myObjects")
    @ResponseBody
    public List<EZObjectView> getMyBObjects(@RequestParam(name = "page", defaultValue="0") int pageno)
    {
        User currentUser = authenticationService.getTheDetailsOfCurrentUser();
        if(currentUser == null)
            throw new AccessDeniedException();
        return ezObjectService.getObjectByOwner(currentUser.getUserName());
    }

    /**
     * @param username username of the owner
     * @param pageno number of the page
     * @return the object of the owner
     */
    @GetMapping("/owner/{username}")
    @ResponseBody
    public List<EZObjectView> getByOwner(@PathVariable String username, @RequestParam(name = "page", defaultValue="0") int pageno)
    {
        return ezObjectService.getObjectByOwner(username);
    }

    /**
     * adds an object with its images
     * @param newObject the new object
     * @param files the images of the object
     * @return a success message if everything is ok
     * @throws Exception
     */
    @PostMapping(value="/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> add(@RequestParam(name = "object") String newObject,
                                      @RequestParam(name = "image") List<MultipartFile> files) throws Exception {


        EZObject obj = mapper.readValue(newObject,EZObject.class);
        ezObjectService.addObject(obj, files);
        return ResponseEntity.ok().body(new SuccessResponse("The object has been stored"));
    }

    /**
     * update the object with its images
     * @param newObject the new Object
     * @param files the images of the object
     * @return a success message if everything is ok
     * @throws Exception
     */
    @PostMapping(value="/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> update(@RequestParam(name = "object") String newObject,
                                         @RequestParam(name = "image") List<MultipartFile> files) throws Exception {


        EZObject obj = mapper.readValue(newObject,EZObject.class);
        ezObjectService.updateObject(obj, files);
        return ResponseEntity.ok().body(new SuccessResponse("The object has been updated"));
    }

    /**
     * delete the object with the id passed in parameter
     * @param id the id of the object
     * @return a success message if the object has been deleted
     * @throws Exception
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Integer id) throws Exception {
        ezObjectService.deleteObject(id);
        return ResponseEntity.ok().body(new SuccessResponse("The object has been deleted"));
    }

    /**
     * delete the image with the id passed in parameter
     * @param id the if of the image
     * @return a success message if the image has been deleted
     * @throws Exception
     */
    @DeleteMapping("/delete/image/{id}")
    public ResponseEntity<SuccessResponse> deleteImage(@PathVariable int id) throws Exception
    {
        ezObjectService.deleteImage(id);
        return ResponseEntity.ok().body(new SuccessResponse("The image has been deleted"));
    }

    /**
     * @param objectName name of the object
     * @param pageno number of the page
     * @return every object with a name which looks like objectName
     */
    @GetMapping("find/name/{objectName}")
    @ResponseBody
    public List<EZObjectView> getByName(@PathVariable String objectName, @RequestParam(name = "page", defaultValue="0") int pageno)
    {
        return ezObjectService.getObjectByName(objectName);
    }

    /**
     * @param content content of object
     * @param pageno number of the page
     * @return every object with the description which looks like content
     */
    @GetMapping("find/description/{content}")
    @ResponseBody
    public List<EZObjectView> getByDescription(@PathVariable String content, @RequestParam(name = "page", defaultValue="0") int pageno)
    {
        return ezObjectService.getObjectByDescription(content);
    }

    /**
     * @param lat
     * @param lng
     * @param pageno  number of the page
     * @return every object with the latitude and longitude (lat and lng)
     */
    @GetMapping("find/localisation")
    @ResponseBody
    public List<EZObjectView> getByLocalisation(@RequestParam(name = "Latitude") BigDecimal lat, @RequestParam(name = "Longitude") BigDecimal lng, @RequestParam(name = "page", defaultValue="0") int pageno)
    {
        return ezObjectService.getObjectsByLocalisation(lat,lng);
    }

    /**
     * @param tags list of the tags
     * @param pageno number of the page
     * @return every object with possed at least one of the tags
     */
    @GetMapping("find/tags")
    @ResponseBody
    public List<EZObjectView> getByTags(@RequestBody List<Tag> tags, @RequestParam(name = "page", defaultValue="0") int pageno)
    {
        return ezObjectService.getObjectsByTag(tags);
    }

    /**
     * return the objects with filters applied
     * @param names list of the names of the object
     * @param owners list of the usernames of the owner
     * @param description list of the description of the object
     * @param tags list of the tags of the object
     * @param pageno number of page
     * @return the list of views on the objects
     */
    @GetMapping("filter")
    public List<EZObjectView> findFiltered(
            @RequestParam(name = "names", required = false) List<String> names,
            @RequestParam(name = "owners", required = false) List<String> owners,
            @RequestParam(name = "description", required = false) List<String> description,
            @RequestParam(name = "tags", required = false) List<Tag> tags, 
            @RequestParam(name = "page", defaultValue="0") int pageno)

    {
        List<EZObject> objects = ezObjectService.getFiltered(names,owners,description,tags, pageno);


        return objects.stream().map(this::convertToView).collect(Collectors.toList());
    }

    /**
     * return the number of the obejct with filters applied
     * @param names list of the names of the object
     * @param owners list of the usernames of the owner
     * @param description list of the description of the object
     * @param tags list of the tags of the object
     * @param pageno number of page
     * @return the number of the object
     */
    @GetMapping("filter/count")
    public ResponseEntity<Integer> getFilteredCount(
            @RequestParam(name = "names", required = false) List<String> names,
            @RequestParam(name = "owners", required = false) List<String> owners,
            @RequestParam(name = "description", required = false) List<String> description,
            @RequestParam(name = "tags", required = false) List<Tag> tags,
            @RequestParam(name = "page", defaultValue="0") int pageno)

    {
        return ResponseEntity.ok(ezObjectService.getFilteredCount(names,owners,description,tags, pageno));
    }

    /**
     * @return the list of all the reported objects
     */
    @GetMapping("find/report")
    public List<EZObjectView> findReportedObject()
    {
        return ezObjectService.getReportedObject();
    }

    /**
     * @param id
     * @return the images for an id given
     */
    @GetMapping("/images/{id}")
    @ResponseBody
    public List<EZObjectImage> getObjecImagePath(@PathVariable int id)
    {
        return ezObjectService.getObjectImages(id);
    }

    /**
     * @param obj object to convert
     * @return a view for a object given
     */
    public EZObjectView convertToView(EZObject obj)
    {
        User owner  = obj.getOwner();
        Address address = owner.getAddress();

        return new EZObjectView() {
            @Override
            public int getID() {
                return obj.getID();
            }

            @Override
            public Set<Tag> getObjectTags() {
                return obj.getObjectTags();
            }

            @Override
            public String getName() {
                return obj.getName();
            }

            @Override
            public String getDescription() {
                return obj.getDescription();
            }

            @Override
            public List<EZObjectImage> getImages() {
                return obj.getImages();
            }

            @Override
            public UserView getOwner() {
                return new UserView() {
                    @Override
                    public String getUserName() {
                        return owner.getUserName();
                    }

                    @Override
                    public String getEmail() {
                        return owner.getEmail();
                    }

                    @Override
                    public AddressView getAddress() {
                        return new AddressView() {
                            @Override
                            public String getAddress() {
                                return address.getAddress();
                            }

                            @Override
                            public String getPostalCode() {
                                return address.getPostalCode();
                            }

                            @Override
                            public BigDecimal getLat() {
                                return address.getLat();
                            }

                            @Override
                            public BigDecimal getLng() {
                                return address.getLng();
                            }
                        };
                    }
                };
            }
        };
    }
}
