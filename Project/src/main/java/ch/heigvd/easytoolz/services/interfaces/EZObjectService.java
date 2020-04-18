package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.views.EZObjectView;
import ch.heigvd.easytoolz.models.Tag;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface EZObjectService {
    /**
     * Add an object into the database
     *
     * @param newObject
     * @return
     */
    void addObject(EZObject newObject);

    /**
     * Updates an object into the database
     *
     * @param o
     * @return
     */
    void updateObject(EZObject o);

    void deleteObject(int id);

    /**
     * Get the list of all the objects
     *
     * @return
     */
    List<EZObjectView> getAll();


    /**
     * Find objects by owner
     * url: api/objects/find/{username}
     *
     * @param username
     * @return
     */
    List<EZObjectView> getObjectByOwner(String username);

    /**
     * Find an object by its ID
     *
     * @param id
     * @return
     */
    EZObjectView getObject(int id);

    /**
     * Find an object from the database
     *
     * @param objectName
     * @return
     */

    List<EZObjectView> getObjectByName(String objectName);

    /**
     * Find objects via description content
     *
     * @param content
     * @return
     */

    List<EZObjectView> getObjectByDescription(String content);


    List<EZObjectView> getObjectsByLocalisation(BigDecimal lat, BigDecimal lng);


    List<EZObjectView> getObjectsByTag(List<Tag> tags);

     List<EZObject> getFiltered( List<String> namesList,
                                           List<String> ownersList,
                                           List<String> descriptionList,
                                 List<Tag> tags);


}