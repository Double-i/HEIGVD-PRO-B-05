package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EzObjectImageRepository extends JpaRepository<EZObjectImage,Integer> {

    /**
     * @param id
     * @return all image files by ID
     */
    List<EZObjectImage> findByEzObject_ID(int id);

    /**
     * @param id
     * @return image by ID
     */
    EZObjectImage findByID(int id);

}
