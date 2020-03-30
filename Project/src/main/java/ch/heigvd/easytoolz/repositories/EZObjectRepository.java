package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.EZObjectView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    List<EZObject>  findByOwner_UserName(String owner);

    /**
     * Find an object by it's ID
     * @param id id of the object
     * @return an object with the corresponding ID
     */
    EZObject findByID(int id);


    List<EZObject> findByDescriptionContaining(String content);

    EZObject findByLocalisation(int localisation);


    //List<EZObjectView>  findByOwner(String owner);

}
