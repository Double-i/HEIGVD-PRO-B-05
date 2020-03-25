package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="EZObject")
public class EZObject {

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getLocalisation() {
        return localisation;
    }

    public void setLocalisation(Integer localisation) {
        this.localisation = localisation;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotNull
    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @NotNull
    @Column(name="owner")
    private String owner;

    @Column(name="localisation")
    private Integer localisation;

    public EZObject(){}
    public EZObject( String name, String description, String owner, Integer localisation) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.localisation = localisation;
    }

    @Override
    public String toString() {
        return "EZObject{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", localisation='" + localisation + '\'' +
                "}\n";
    }
}
