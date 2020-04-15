package ch.heigvd.easytoolz.views;

import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.EZObjectImage;
import ch.heigvd.easytoolz.models.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Immutable
@Table(name = "ezobjectview")
public class EZObjectView {

    public int getID(){return objectId;}

    public EZObject getEzObject() {
        return ezObject;
    }

    public void setEzObject(EZObject ezObject) {
        this.ezObject = ezObject;
    }

    public List<EZObjectImage> getImages() {
        return ezObject.getImages();
    }

    public Set<Tag> getObjectTag() {
        return ezObject.getObjectTags();
    }

    public int getObjectId() {
        return objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getObjectDescription() {
        return objectDescription;
    }

    public String getObjectOwner() {
        return objectOwner;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public String getOwnerDistrict() {
        return ownerDistrict;
    }

    public String getOwnerPostalCode() {
        return ownerPostalCode;
    }

    public BigDecimal getOwnerLatitude() {
        return ownerLatitude;
    }

    public BigDecimal getOwnerLongitude() {
        return ownerLongitude;
    }

    @Id
    int objectId;
    String objectName;
    String objectDescription;
    String objectOwner;
    String ownerAddress;
    String ownerDistrict;
    String ownerPostalCode;
    BigDecimal ownerLatitude;
    BigDecimal ownerLongitude;


    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "object_id", referencedColumnName = "id")
    EZObject ezObject;



    public EZObjectView() {
    }
}
