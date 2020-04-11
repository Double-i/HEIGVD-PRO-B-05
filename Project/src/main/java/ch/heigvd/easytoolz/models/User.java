package ch.heigvd.easytoolz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
public class User {


    @Id
    @NotNull
    private String userName;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private boolean isAdmin;

    @OneToMany(mappedBy = "owner")
    private List<EZObject> ezObject;

    public List<EZObject> getEzObject() {
        return ezObject;
    }

    public void setEzObject(List<EZObject> ezObject) {
        this.ezObject = ezObject;
    }

    // Required for creating JSON parsing
    public User(){}

    @ManyToOne(optional = false)
    @JoinColumn(name="fk_address", referencedColumnName = "id")
    private Address address;

    public User(String userName, String firstName, String lastName, String password, String email, Address address, boolean isAdmin){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        // TODO : Hasher le mot de passe
        this.password = password;
        this.isAdmin = isAdmin;
        this.address  = address;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Address getAddress(){return address;}
    public void setAddress(Address address) { this.address = address;}

    @Override
    public String toString() {
        return String.format(
                "User{" +
                        "username=%s," +
                        "firstName=%s," +
                        "lastName=%s," +
                        "isAdmin=%b" +
                        "}",
                userName,
                firstName,
                lastName,
                isAdmin);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
