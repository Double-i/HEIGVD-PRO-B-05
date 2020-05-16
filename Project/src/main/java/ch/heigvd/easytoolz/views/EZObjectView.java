package ch.heigvd.easytoolz.views;

import ch.heigvd.easytoolz.models.*;
import java.util.List;
import java.util.Set;


public interface EZObjectView {

    int getID();
    Set<Tag> getObjectTags();
    String getName();
    String getDescription();
    List<EZObjectImage> getImages();
    UserView getOwner();
}
