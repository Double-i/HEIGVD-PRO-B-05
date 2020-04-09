package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="City")
public class City {

    public Country getCountry() { return country; }
    public String getCity() { return city; }

    public void setCity(String city) {this.city = city;}
    public void setCountry(Country country) { this.country = country;  }

    public int getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="city")
    String city;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "fkcountry", referencedColumnName = "id")
    private Country country;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "city")
    List<Address> adresses;

    public City() {}


    public City(String city)
    {
        this.city = city;
    }
}
