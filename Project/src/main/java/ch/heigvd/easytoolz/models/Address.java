package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Address {

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getDistrict() {
        return district;
    }

    public String getPostalCode() {
        return postalcode;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public City getCity() {
        return city;
    }

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

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public void setCity(City city) {
        this.city = city;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String address;
    String district;
    String postalcode;

    @Column(precision = 10, scale = 5)
    BigDecimal lat;

    @Column(precision = 10, scale = 5)
    BigDecimal lng;

    @OneToMany(mappedBy = "address")
    private List<User> user;

    @ManyToOne
    @JoinColumn(name = "fk_city", referencedColumnName = "id")
    private City city;

    public Address() {

    }

    public Address(String address, String district, String postalCode, BigDecimal lat, BigDecimal lng, City city) {
        this.address = address;
        this.district = district;
        this.postalcode = postalCode;
        this.lat = lat;
        this.lng = lng;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", district='" + district + '\'' +
                ", postalcode='" + postalcode + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", user=" + user +
                ", city=" + city +
                '}';
    }
}
