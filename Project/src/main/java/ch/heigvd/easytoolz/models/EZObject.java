package ch.heigvd.easytoolz.models;

import javax.persistence.*;

@Entity
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

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int ID;
    private String name;
    private String description;
    private String owner;
    private int tagID;

    public EZObject(){}
    public EZObject(int ID, String name, String description, String owner, int tagID) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.tagID = tagID;
    }

    @Override
    public String toString() {
        return "EZObject{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", tagID=" + tagID +
                "}\n";
    }
}
