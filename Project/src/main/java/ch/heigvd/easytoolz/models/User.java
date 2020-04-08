package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="user")
public class User {
    @Id
    @Column(name="username")
    @NotNull
    private String userName;
    @Column(name="firstname")
    @NotNull
    private String firstName;
    @Column(name="lastname")
    @NotNull
    private String lastName;
    @Column(name="password")
    @NotNull
    private String password;
    @Column(name="email")
    @NotNull
    private String email;
    @Column(name="isadmin")
    @NotNull
    private boolean isAdmin;

    @OneToMany(mappedBy = "owner")
    private List<EZObject> ezobject;

    // Required for creating JSON parsing
    public User(){}

    @ManyToOne
    @JoinColumn(name="fkaddress", referencedColumnName = "id")
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
