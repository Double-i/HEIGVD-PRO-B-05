package ch.heigvd.easytoolz.models;

import javax.persistence.*;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String city;

    @ManyToOne
    @JoinColumn(name = "fkcountry", referencedColumnName = "id")
    private Country country;

    public City(){
    }

    public City(String city, Country country){
        this.city = city;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
