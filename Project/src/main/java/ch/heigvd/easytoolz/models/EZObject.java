package ch.heigvd.easytoolz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;


/**
 * test
 * Data model for an object
 */
@Entity
@Where(clause = "is_active=1")
public class EZObject {

    /**
     * Return the ID of the object
     * @return int object's ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Returns the name of the object
     * @return String object's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the object
     * @return String object's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the owner of the object via the User object
     * @see User#getUserName()  User object
     * @return String objects owner string
     */
    @Access(AccessType.FIELD)
    public String getOwnerUserName() {
        if (ownerUserName == null) {
            ownerUserName = owner.getUserName();
        }
        return ownerUserName;
    }

    /**
     * Retunrs a list of images related to the current object
     * @return List<EZObjectImage>
     */
    public List<EZObjectImage> getImages() {
        return images;
    }

    /**
     * Returns a list of tags related to the current object
     * @return List<Tag>
     */
    public Set<Tag> getObjectTags() {
        return objectTags;
    }

    /**
     * Returns wether the current object is active or not
     * Is used for handling soft delete in the database
     * @return Boolean
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Returns the object owner
     * @see ch.heigvd.easytoolz.models.User User object
     * @return User
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Set the current object's ID
     * @param ID id of the objectr
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Set the current object's name
     * @param name name of the object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the current object's description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * set the current object's images
     * @param ezobject
     */
    public void setImages(List<EZObjectImage> ezobject) {
        this.images = ezobject;
    }

    /**
     * Set the current object's tags
     * @param objectTags
     */
    public void setObjectTags(Set<Tag> objectTags) {
        this.objectTags = objectTags;
    }

    /**
     * Set the current object's owner
     * @see ch.heigvd.easytoolz.models.User
     * @param owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Set the current object'es active state
     * @param active
     */
    public void setActive(boolean active) {
        this.isActive = active;
    }


    /**
     * This field is auto generated in the database
     * And represents an Unique Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotNull
    private String name;

    private String description;

    /**
     * Upon creation this field will always be equals to 1
     */
    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean isActive = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ezObject")
    private List<EZObjectImage> images;

    @Transient
    public String ownerUserName;

    /**
     * Do not include the full owner in the JSON file when returned
     * Join with the corresponding user
     * Cannot be null
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "userName", nullable = false)
    private User owner;

    /**
     * assisiation between {@link EZObject} and {@link Tag}
     * tag list are always included in the object JSON
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ezobject_tag",
            joinColumns = @JoinColumn(name = "fk_ezobject"),
            inverseJoinColumns = @JoinColumn(name = "fk_tag")
    )
    Set<Tag> objectTags;

    /**
     * default constructor
     */
    public EZObject() {
    }

    public EZObject(String name, String description, Set<Tag> tags, List<EZObjectImage> images) {
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
