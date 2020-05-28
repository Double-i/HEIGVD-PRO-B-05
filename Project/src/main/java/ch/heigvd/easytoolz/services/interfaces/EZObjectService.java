package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.exceptions.ezobject.UserHasNoObjectException;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.EZObjectImage;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.models.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface EZObjectService {
    /**
     * Add an object into the database
     *
     * @param newObject new object to be added
     * @param files files linked to the object( Images )
     * @return
     */
    void addObject(EZObject newObject, List<MultipartFile> files ) throws Exception;

    /**
     * Updates an object into the database
     * @param object object to be updated
     * @param files files to be uploaded
     * @return
     */
    void updateObject(EZObject object, List<MultipartFile> files) throws Exception;

    /**
     * Soft delete an object and delete its corresponding images
     * @param id id of the object tot delete
     * @throws Exception
     */
    void deleteObject(int id) throws Exception;
    void deleteImage(int id) throws Exception;


    /**
     * Get a list of object with pagination
     * @param page desired page
     * @param pageLength number of object to be contained in to the page
     * @return EZObjectArray array
     */
    List<EZObjectView> getAll(int page, int pageLength);


    /**
     * Find an object by its owner
     *
     *
     * @param username name of the owner
     * @return object array
     */
    List<EZObjectView> getObjectByOwner(String username) throws UserHasNoObjectException;

    /**
     * Get an object by its ID
     *
     * @param id id of the object
     * @return
     */
    EZObjectView getObject(int id);

    /**
     * Find object by its object by the content of the name
     *
     * @param objectName
     * @return liste d'objet
     */

    List<EZObjectView> getObjectByName(String objectName);

    /**
     * Find object by the content of the description
     *
     * @param content
     * @return
     */
    List<EZObjectView> getObjectByDescription(String content);

    /**
     * Find an object by its localisation
     *
     * @param lat
     * @param lng
     * @return
     */
    List<EZObjectView> getObjectsByLocalisation(BigDecimal lat, BigDecimal lng);

    /**
     * find object by their tags
     *
     * @param tags
     * @return
     */
    List<EZObjectView> getObjectsByTag(List<Tag> tags);

    /**
     * Counts object with a filter
     *
     * @param namesList arrays of names to be filtered
     * @param ownersList arrays of owner to be filtered
     * @param descriptionList liste  array of description keywords to be filtered
     * @param tags array of tags to be filtered
     * @param page desired page
     * @return array of object
     */
    int getFilteredCount(List<String> namesList,
                         List<String> ownersList,
                         List<String> descriptionList,
                         List<Tag> tags ,int page);
    /**
     * get a list of filtered
     *
     * @param namesList arrays of names to be filtered
     * @param ownersList arrays of owner to be filtered
     * @param descriptionList liste  array of description keywords to be filtered
     * @param tags array of tags to be filtered
     * @param page desired page
     * @return array of object
     */
     List<EZObject> getFiltered(List<String> namesList,
                                List<String> ownersList,
                                List<String> descriptionList,
                                List<Tag> tags ,int page);
    /**
     * Returns a list of image files
     *
     * @param id
     * @return List of image files
     */
    List<EZObjectImage> getObjectImages(int id);

    /**
     * get reported object
     *
     * @return array of objects
     */
    List<EZObjectView> getReportedObject();



}
