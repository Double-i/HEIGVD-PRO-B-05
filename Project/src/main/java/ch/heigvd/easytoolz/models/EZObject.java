package ch.heigvd.easytoolz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    public Integer getLocalisation() {
        return localisation;
    }

    public void setLocalisation(Integer localisation) {
        this.localisation = localisation;
    }

    public User getOwner(){return owner;}

    public void setOwner(User owner){this.owner = owner;}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotNull
    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="localisation")
    private Integer localisation;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "owner", referencedColumnName = "userName")
    private User owner;


    public EZObject(){}
    public EZObject( String name, String description ,User owner, Integer localisation) {
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
