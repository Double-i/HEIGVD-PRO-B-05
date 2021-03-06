package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.Loan;
import ch.heigvd.easytoolz.models.Tag;
import ch.heigvd.easytoolz.views.EZObjectView;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.math.BigDecimal;
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
     *
     * @param tool EZObject
     * @param dateStart Start Date
     * @param dateEnd End Date
     * @return boolean return true if already used
     */
    @Query("SELECT CASE WHEN COUNT(lo) > 0 THEN true ELSE false END FROM Loan lo" +
            " INNER JOIN Period peri ON lo.pkLoan = peri.loan.pkLoan" +
            " WHERE peri.state = :periodState " +
            " AND lo.state = :toolState " +
            " AND lo.EZObject = :tool"  +
            " AND ((:dateStart BETWEEN peri.dateStart AND peri.dateEnd )" +  // get all loans with dateStart during another loan period
            " OR (:dateEnd BETWEEN peri.dateStart AND peri.dateEnd)" +     // get all loans with dateEnd during another loan period
            " OR (peri.dateStart BETWEEN  :dateStart AND :dateEnd))")     // get all loans with dateStart during the new loan period
    boolean isAlreadyBorrow(EZObject tool, Date dateStart, Date dateEnd, State toolState, State periodState);


    /**
     * Return true if the tool is already (accepted) borrowed for a given period (between dateStart and dateEnd)
     * This version of isAlreadyBorrow exclude the loan we've given in the parameters
     *
     * @param tool the tool of the loans
     * @param loan the loan to ignore
     * @param dateStart start date period
     * @param dateEnd end date period
     * @param toolState the tool state we're interested in
     * @param periodState the period state we're interested in
     * @return
     */
    @Query("SELECT lo FROM Loan lo" +
            " INNER JOIN Period peri ON lo.pkLoan = peri.loan.pkLoan" +
            " WHERE peri.state = :periodState " +
            " AND lo <> :loan" +                                         // ignore the loan related to the given loan
            " AND lo.state = :toolState " +
            " AND lo.EZObject = :tool"  +
            " AND ((:dateStart BETWEEN peri.dateStart AND peri.dateEnd )" +     // get all loans with dateStart during another loan period
            " OR (:dateEnd BETWEEN peri.dateStart AND peri.dateEnd)" +          // get all loans with dateEnd during another loan period
            " OR (peri.dateStart BETWEEN  :dateStart AND :dateEnd))")           // get all loans with dateStart during the new loan period
    List<Loan> overlapLoans(EZObject tool, Loan loan, Date dateStart, Date dateEnd, State toolState, State periodState);





    //REQUETES SUR DES PROJECTIONS(VUES)

    /**
     * get object by IDs
     * @param id
     * @return
     */
    EZObjectView getEZObjectByID(int id);

    /**
     * get all active objects
     * @param active
     * @param page
     * @return
     */
    Page<EZObjectView> getAllByIsActive(boolean active, Pageable page);

    /**
     * get object by username
     * @param username
     * @return
     */
    List<EZObjectView> getByOwner_UserName(String username);

    /**
     * get object by content of its name
     * @param name
     * @return
     */
    List<EZObjectView> getAllByNameContaining(String name);

    /**
     * get object by content of its description
     * @param name
     * @return
     */
    List<EZObjectView> getAllByDescriptionContaining(String name);

    /**
     * get object by geographic coordinates(from the current user)
     * @param lat
     * @param lng
     * @return
     */
    List<EZObjectView> getAllByOwner_Address_LatAndOwner_Address_Lng(BigDecimal lat,BigDecimal lng);

    /**
     * get all object by tags
     * @param tags
     * @return
     */
    List<EZObjectView> getAllByObjectTagsIn(List<Tag> tags);

    /**
     * get all reported object
     * @return
     */
    @Query("SELECT e.ID FROM EZObject e" +
            " WHERE e.ID IN (SELECT r.EZObject FROM Report r)")
    List<Integer> getReportedObject();

    /**
     * get all object by IDs in List
     * @param ezObjectListID
     * @return
     */
    List<EZObjectView> getAllByIDIn(List<Integer> ezObjectListID);

    /**
     * Count all active objects
     * @param active
     * @return
     */
    int countAllByIsActive(boolean active);

}
