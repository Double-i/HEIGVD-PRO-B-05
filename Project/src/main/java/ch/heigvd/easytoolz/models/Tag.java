package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tag")
public class Tag {
    public int getId() {
        return pktag;
    }

    public void setId(int id) {
        this.pktag = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pktag;

    private String name;

    @ManyToMany(mappedBy = "objecttags")
    Set<EZObject> tagobjects;

    public Tag(){}

    public Tag(String name) {
    }


}
