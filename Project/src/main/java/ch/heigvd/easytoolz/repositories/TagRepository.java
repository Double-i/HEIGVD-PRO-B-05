package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,String> {
    List<Tag> findByNameLike(String name);
}
