package ch.heigvd.easytoolz.models.dto;

import ch.heigvd.easytoolz.models.User;

import java.util.Date;

public class AuthentificationResponse {

    public AuthentificationResponse(User user, Date tokenDuration){
        this.user = user;
        this.tokenDuration = tokenDuration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTokenDuration() {
        return tokenDuration;
    }

    public void setTokenDuration(Date tokenDuration) {
        this.tokenDuration = tokenDuration;
    }

    User user;
    Date tokenDuration;

}
