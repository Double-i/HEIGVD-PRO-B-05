package ch.heigvd.easytoolz.models;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String userName;
    private String password;



    //need default constructor for JSON Parsing
    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.setUserName(username);
        this.setPassword(password);
    }
    public String toString(){
        return String.format("%s={%s %s}", AuthenticationRequest.class, userName, password);
    }
}