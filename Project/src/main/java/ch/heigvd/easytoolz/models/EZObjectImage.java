package ch.heigvd.easytoolz.models;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
public class EZObjectImage {

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathtoimg) {
        this.pathToImage = pathtoimg;
    }

    public void setObject(EZObject obj){ezObject = obj;}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String pathToImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_ezobject", referencedColumnName = "id")
    private EZObject ezObject;

    public EZObjectImage() {
    }


}
