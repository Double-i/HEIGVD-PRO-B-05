package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import ch.heigvd.easytoolz.models.State;

/**
 *
 */
public interface EZObjectRepository extends JpaRepository<EZObject, String> {


    /**
     * Finds an object by its name
     *
     * @param name name of the object
     * @return find an object with similar names
     */
    List<EZObject> findByNameContaining(String name);


    /**
     * Find an object by it's owner
     *
     * @param owner owner of the object
     * @return a list of object from the same owner
     */
    List<EZObject> findByOwner_UserName(String owner);

    /**
     * Find an object by it's ID
     *
     * @param id id of the object
     * @return an object with the corresponding ID
     */
    EZObject findByID(int id);

    List<EZObject> findByDescriptionContaining(String content);

    /**
     * Return true if the tool is already (accepted) borrowed for a given period (between dateStart and dateEnd)
     * TODO : add WHERE clause to exclude pending/refused query (but state must be defined)
     *
     * @param tool EZObject
     * @param dateStart Start Date
     * @param dateEnd End Date
     * @return boolean return true if already used
     */
    @Query("SELECT CASE WHEN COUNT(lo) > 0 THEN true ELSE false END FROM Loan lo" +
            " WHERE lo.EZObject = :tool AND lo.state = :state"  +
            " AND ((:dateStart BETWEEN lo.dateStart AND lo.dateEnd )" +  // get all loans with dateStart during another loan period
            " OR (:dateEnd BETWEEN lo.dateStart AND lo.dateEnd)" +     // get all loans with dateEnd during another loan period
            " OR (lo.dateStart BETWEEN  :dateStart AND :dateEnd))")     // get all loans with dateStart during the new loan period*/
    boolean isAlreadyBorrow(EZObject tool, Date dateStart, Date dateEnd, State state);

    //EZObject findByLocalisation(int localisation);

    //List<EZObjectView>  findByOwner(String owner);

}
