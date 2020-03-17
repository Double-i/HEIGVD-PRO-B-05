package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.Material.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface MaterialRepository extends JpaRepository<Material,String> {
    List<Material>  findByName(String name);
    List<Material>  findByTagID(int tag);
    List<Material>  findByOwner(String owner);
    Material        findByID(int id);

}
