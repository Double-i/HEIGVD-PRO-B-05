package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EzObjectImageRepository extends JpaRepository<EZObjectImage,Integer> {

    /**
     * get all image files by ID
     * @param id
     * @return
     */
    List<EZObjectImage> findByEzObject_ID(int id);

    /**
     * get image by ID
     * @param id
     * @return
     */
    EZObjectImage findByID(int id);

}
