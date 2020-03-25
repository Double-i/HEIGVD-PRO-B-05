package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.EZObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface EZObjectRepository extends JpaRepository<EZObject,String> {
    List<EZObject>  findByName(String name);
    List<EZObject>  findByTagID(int tag);
    List<EZObject>  findByOwner(String owner);
    EZObject findByID(int id);

}
