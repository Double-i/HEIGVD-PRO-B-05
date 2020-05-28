package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.models.Tag;

import java.util.List;

public interface TagService {
    /**
     * @return every tag in the database
     */
    List<Tag> getAll();

    /**
     * @param name
     * @return  every tag which has the parameter "name" in their "name"
     *          for example :
     *          tags = "Marteau", "Pelle", "Perceuse"
     *          name -> "pe"
     *          then, the method will return :
     *          tags -> "Pelle", "Perceuse"
     */
    List<Tag> getByName(String name);
}
