package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public interface EZObjectRepository extends JpaRepository<EZObject,String> {


    /**
     * Finds an object by its name
     * @param name name of the object
     * @return find an object with similar names
     */
    List<EZObject>  findByNameContaining(String name);


    /**
     * Find an object by it's owner
     * @param owner owner of the object
     * @return a list of object from the same owner
     */
    List<EZObject>  findByOwner(String owner);

    /**
     * Find an object by it's ID
     * @param id id of the object
     * @return an object with the corresponding ID
     */
    EZObject findByID(int id);

    List<EZObject> findByDescriptionContaining(String content);

    /**
     * Find an object by its coordinates
     * @param lat
     * @param lng
     * @return
     */
    List<EZObject> findByOwner_Address_LatAndOwner_Address_Lng(BigDecimal lat, BigDecimal lng);

    List<EZObject> findByObjectTagsIn(List<Tag> tags);


}
