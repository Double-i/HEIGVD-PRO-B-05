package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tag")
public class Tag {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @NotNull
    private String name;


    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "objectTags")
    Set<EZObject> tagObjects;

    public Tag(){}
    public Tag(String name) {this.name = name;}


}
