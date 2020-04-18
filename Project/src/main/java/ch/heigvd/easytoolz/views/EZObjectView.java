package ch.heigvd.easytoolz.views;

import ch.heigvd.easytoolz.models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.math.BigDecimal;
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
