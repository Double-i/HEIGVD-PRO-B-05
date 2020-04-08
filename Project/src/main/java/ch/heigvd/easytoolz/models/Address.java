package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "adress")
public class Address {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostalCode() {
        return postalcode;
    }

    public void setPostalCode(String postalCode) {
        this.postalcode = postalCode;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }
    public City getCity() { return city; }

    public void setId(int id) {
        this.id = id;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public void setPostalCode(String postalCode) {
        this.postalcode = postalCode;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Address(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String address;
    String district;
    String postalcode;
    float lat;
    float lng;

    @OneToMany(mappedBy = "address")
    private List<User> user;

    @ManyToOne
    @JoinColumn(name = "fkCity", referencedColumnName = "id")
    private City city;


    public Address( String address, String district, String postalCode, float lat, float lng) {
        this.address = address;
        this.district = district;
        this.postalcode = postalCode;
        this.lat = lat;
        this.lng = lng;
    }


}
