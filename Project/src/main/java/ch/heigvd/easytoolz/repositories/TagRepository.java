package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,String> {
}
