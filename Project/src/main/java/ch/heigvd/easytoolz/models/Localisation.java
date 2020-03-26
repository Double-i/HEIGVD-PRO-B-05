package ch.heigvd.easytoolz.models;

import javax.persistence.*;

@Entity
public class Localisation {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Id
    int id;

    @Column(name=("latitude"))
    float latitude;

    @Column(name=("longitude"))
    float longitude;

    public Localisation(){}
    public Localisation(float latitude, float longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
