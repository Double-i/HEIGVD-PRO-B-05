package ch.heigvd.easytoolz.user;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(name="isadmin")
    @NotNull
    private boolean isAdmin;

    public User() {}

    public User(String userName, String firstName, String lastName, String password, boolean isAdmin){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        // TODO : Hasher le mot de passe
        this.password = password;
        this.isAdmin = isAdmin;
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
}
