package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EzObjectImageRepository extends JpaRepository<EZObjectImage,Integer> {

    /**
     * permet de récuperer une liste de noms de fichier lié a un objet
     * @param id
     * @return
     */
    List<EZObjectImage> findByEzObject_ID(int id);

    /**
     * permet de récuperer un nom de fichier via son ID
     * @param id
     * @return
     */
    EZObjectImage findByID(int id);

}
