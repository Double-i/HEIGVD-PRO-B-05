package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObject;
import org.springframework.data.jpa.repository.JpaRepository;

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
    List<EZObject>  findByName(String name);

    /**
     * Find an object by it's tag
     * @param tag ID of the tag
     * @return a list of object with the same tag ID
     */
    List<EZObject>  findByTagID(int tag);

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

}
