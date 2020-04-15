package ch.heigvd.easytoolz.models;


import javax.persistence.*;
import java.util.List;

@Entity
public class Country {

    public int getId() {   return id;  }
    public String getCountry() { return country;}

    public void setCountry(String country) { this.country = country; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @Column(name="country")
    String country;

    @OneToMany(fetch=FetchType.EAGER,mappedBy = "country")
    List<City> cities;



    public Country() {}

    public Country(String country) {
        this.country= country;
    }
}
