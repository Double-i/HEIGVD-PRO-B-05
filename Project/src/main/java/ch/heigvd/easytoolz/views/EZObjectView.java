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
@Table(name="ezobjectview")
public class EZObjectView {

    @Id
    @Column(name = "object_id")
    int objectId;
    @Column(name = "object_name")
    String objectName;
    @Column(name = "object_description")
    String objectDescription;
    @Column(name = "object_owner")
    String objectOwner;
    @Column(name = "owner_address")
    String ownerAddress;
    @Column(name = "owner_district")
    String ownerDistrict;
    @Column(name = "owner_postal_code")
    String ownerPostalCode;
    @Column(name = "owner_latitude")
    BigDecimal ownerLat;
    @Column(name = "owner_longitude")
    BigDecimal ownerLng;

    @JsonIgnore
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "object_id", referencedColumnName = "id")
    EZObject obj;


    public List<EZObjectImage> getObjectImage()
    {
        return obj.getImages();
    }

    public Set<Tag> getObjectTag() {
        return obj.getObjecttags();
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

    public BigDecimal getOwnerLat() {
        return ownerLat;
    }

    public BigDecimal getOwnerLng() {
        return ownerLng;
    }

    public EZObjectView(){}
}
