package ch.heigvd.easytoolz.models;

import javax.persistence.*;

@Entity
@Table(name="ezobjectimage")
public class EZObjectImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String pathtoimg;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "fkezobject", referencedColumnName = "id")
    private EZObject objectimg;

    public EZObjectImage() {
    }

    public String getPathtoimg() {
        return pathtoimg;
    }

    public void setPathtoimg(String pathtoimg) {
        this.pathtoimg = pathtoimg;
    }
}
