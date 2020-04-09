package ch.heigvd.easytoolz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="EZObject")
public class EZObject {

    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public String getOwnerUserName(){return this.ownerUserName; }
    public List<EZObjectImage> getImages() {
        return images;
    }
    public Set<Tag> getObjecttags() {
        return objecttags;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setOwnerUserName(String owner){this.ownerUserName = owner;}
    public void setImages(List<EZObjectImage> ezobject) {
        this.images = ezobject;
    }
    public void setObjecttags(Set<Tag> objecttags) {
        this.objecttags = objecttags;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotNull
    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;


//@JsonIgnore
    //@ManyToOne(fetch=FetchType.LAZY)
   // @JoinColumn(name = "owner", referencedColumnName = "userName")

    @Column(name="owner", updatable = false, insertable = false)
    private String ownerUserName;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "objectimg")
    private List<EZObjectImage> images;

     @JsonIgnore
     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name = "owner", referencedColumnName = "userName")
     private User owner;

    //association class
    @ManyToMany
            @JoinTable(
                    name="ezobjecttag",
                    joinColumns = @JoinColumn(name="fkezobject"),
                    inverseJoinColumns = @JoinColumn(name="fktag")
            )
    Set<Tag> objecttags;

    public EZObject(){}
    public EZObject(String name, String description , String ownerUserName, Set<Tag> tags, List<EZObjectImage> images) {
        this.name = name;
        this.description = description;
        this.ownerUserName = ownerUserName;
        this.objecttags = tags;
        this.images = images;
    }

    @Override
    public String toString() {
        return "EZObject{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + ownerUserName + '\'' +
                "}\n";
    }
}
