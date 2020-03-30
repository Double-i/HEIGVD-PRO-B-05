package ch.heigvd.easytoolz.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
        return fkCity;
    }

    public void setFkCity(int fkCity) {
        this.fkCity = fkCity;
    }


    public Address(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String address;

    String district;

    String postalCode;

    float lat;

    float lng;

    int fkCity;

    public Address( String address, String district, String postalCode, float lat, float lng, int fkCity) {
        this.address = address;
        this.district = district;
        this.postalCode = postalCode;
        this.lat = lat;
        this.lng = lng;
        this.fkCity = fkCity;
    }


}
