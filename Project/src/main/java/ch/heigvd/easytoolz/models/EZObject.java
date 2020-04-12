package ch.heigvd.easytoolz.models;

import ch.heigvd.easytoolz.views.EZObjectView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Where(clause = "is_active=1")
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

    @Access(AccessType.FIELD)
    public String getOwnerUserName()
    {
        if(ownerUserName == null)
        {
            ownerUserName = owner.getUserName();
        }
        return ownerUserName;
    }

    public List<EZObjectImage> getImages() {
        return images;
    }

    public Set<Tag> getObjectTags() { return objectTags; }

    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImages(List<EZObjectImage> ezobject) {
        this.images = ezobject;
    }
    public void setObjectTags(Set<Tag> objecttags) {
        this.objectTags = objecttags;
    }


    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotNull
    private String name;

    private String description;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean isActive=true;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "object_image")
    private List<EZObjectImage> images;

    @Transient
    private String ownerUserName;

     @JsonIgnore
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "owner", referencedColumnName = "userName")
     private User owner;

    //association class
    @ManyToMany
    @JoinTable(
            name="ezobject_tag",
            joinColumns = @JoinColumn(name="fk_ezobject"),
            inverseJoinColumns = @JoinColumn(name="fk_tag")
    )
    Set<Tag> objectTags;


    public EZObject(){}
    public EZObject(String name, String description  , Set<Tag> tags, List<EZObjectImage> images)
    {
        this.name = name;
        this.description = description;
        this.objectTags = tags;
        this.images = images;
        this.setActive(true);
    }

    @Override
    public String toString() {
        return "EZObject{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                "}\n";
    }
}
