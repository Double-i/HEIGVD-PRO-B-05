package ch.heigvd.easytoolz.models;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "is_active=1")
public class EZObjectImage {

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathtoimg) {
        this.pathToImage = pathtoimg;
    }

    public void setObject(EZObject obj){ezObject = obj;}

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public int getID(){return ID;}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ID;
    String pathToImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_ezobject", referencedColumnName = "id")
    private EZObject ezObject;

    boolean is_active = true;

    public EZObjectImage() {
    }


}
