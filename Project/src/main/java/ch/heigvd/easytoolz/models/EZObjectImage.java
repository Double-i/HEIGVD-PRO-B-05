package ch.heigvd.easytoolz.models;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name="ezobject_image")
public class EZObjectImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String pathToImage;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "fk_ezobject", referencedColumnName = "id")
    private EZObject object_image;

    public EZObjectImage() {
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathtoimg) {
        this.pathToImage = pathtoimg;
    }
}
