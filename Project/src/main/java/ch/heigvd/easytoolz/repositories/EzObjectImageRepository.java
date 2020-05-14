package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EzObjectImageRepository extends JpaRepository<EZObjectImage,Integer> {

    List<EZObjectImage> findByEzObject_ID(int id);
    EZObjectImage findByPathToImage(String file);
    EZObjectImage findByID(int id);

}
