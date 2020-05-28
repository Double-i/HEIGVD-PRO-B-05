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
     * Ajouter un objet dans la base de données
     *
     * @param newObject objet a rajouter dans la base de données
     * @param files fichier a uploads dans le système
     */
    void addObject(EZObject newObject, List<MultipartFile> files ) throws Exception;

    /**
     * Mise  ajour d'un objet dans la base de données
     *
     * @param object objet à mettre a jour
     */
    void updateObject(EZObject object, List<MultipartFile> files) throws Exception;

    /**
     * Supprime un objet de la base de donnée (soft delete)
     * @param id id de l'objet a supprimer
     * @throws Exception
     */
    void deleteObject(int id) throws Exception;
    void deleteImage(int id) throws Exception;


    /**
     * Récupère une liste de tout les objets présent dans la base de donnée selon une pagination
     * @param page la page désirée
     * @param pageLength le nombre d'objet affiché par page
     * @return liste d'objet
     */
    List<EZObjectView> getAll(int page, int pageLength);


    /**
     * Trouver un objet grâce a son nom d'utilisateur
     *
     *
     * @param username nom d'utilisateur
     * @return liste d'objet
     */
    List<EZObjectView> getObjectByOwner(String username) throws UserHasNoObjectException;

    /**
     * Retrouve un objet unique via son ID
     *
     * @param id id de l'objet a trouver
     * @return
     */
    EZObjectView getObject(int id);

    /**
     * Retrouver  une liste  d'objets grâce au contenu de leurs noms
     *
     * @param objectName
     * @return liste d'objet
     */

    List<EZObjectView> getObjectByName(String objectName);

    /**
     * Trouver  une liste  d'objets grace au contenu de leurs description
     *
     * @param content
     * @return
     */
    List<EZObjectView> getObjectByDescription(String content);

    /**
     * Trouver  une liste  d'objets grâce a leurs localisation
     *
     * @param lat
     * @param lng
     * @return
     */
    List<EZObjectView> getObjectsByLocalisation(BigDecimal lat, BigDecimal lng);

    /**
     * Trouver une liste  d'objets grace a leurs categorie
     *
     * @param tags
     * @return une liste de vue d'objets
     */
    List<EZObjectView> getObjectsByTag(List<Tag> tags);

    /**
     * Permet de retrouver le total d'objet correspondant aux filtres passé en paramètre
     *
     * @param namesList liste des noms qui pourront etre utilise pour filtrer
     * @param ownersList liste des utilsiateurs qui pourront etre utilise pour filtrer
     * @param descriptionList liste  de mot qui peuvent etre utilise pour filtrer
     * @param tags liste des tags qui peuvent etre utilise pour filtrer
     * @param page la page désirée
     * @return nombre d'objet correspondant au filtres passés en paramètre
     */
    int getFilteredCount(List<String> namesList,
                         List<String> ownersList,
                         List<String> descriptionList,
                         List<Tag> tags ,int page);
    /**
     * Permet de retrouver une liste d'objet correspondant aux filtres passé en paramètre
     *
     * @param namesList liste des noms qui pourront etre utilise pour filtrer
     * @param ownersList liste des utilsiateurs qui pourront etre utilise pour filtrer
     * @param descriptionList liste  de mot qui peuvent etre utilise pour filtrer
     * @param tags liste des tags qui peuvent etre utilise pour filtrer
     * @param page la page désirée
     * @return
     */
     List<EZObject> getFiltered(List<String> namesList,
                                List<String> ownersList,
                                List<String> descriptionList,
                                List<Tag> tags ,int page);
    /**
     * Récupere les images liés a un id
     *
     * @param id de l'image désiré
     * @return liste de noms de fichier
     */
    List<EZObjectImage> getObjectImages(int id);

    /**
     * Récupere les objet signalé
     *
     * @return liste d'objets
     */
    List<EZObjectView> getReportedObject();



}
