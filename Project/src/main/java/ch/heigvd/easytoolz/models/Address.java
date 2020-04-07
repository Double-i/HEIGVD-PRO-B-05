package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import java.util.List;

@Entity
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

    public void setLng(float lng) {
        this.lng = lng;
    }

    public int getFkCity() {
        return fkcity;
    }

    public void setFkCity(int fkCity) {
        this.fkcity = fkCity;
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

    int fkcity;

    @OneToMany(mappedBy = "address")
    private List<User> user;

    public Address( String address, String district, String postalCode, float lat, float lng, int fkCity) {
        this.address = address;
        this.district = district;
        this.postalcode = postalCode;
        this.lat = lat;
        this.lng = lng;
        this.fkcity = fkCity;
    }


}
