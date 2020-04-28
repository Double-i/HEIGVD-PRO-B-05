package ch.heigvd.easytoolz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Notification")
public class Notification {

    public int getID() {
        return ID;
    }

    public String getMessage() {
        return message;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotNull
    private String message;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    @JoinColumn(name = "fk_user", referencedColumnName = "userName")
    private User recipient;


}
