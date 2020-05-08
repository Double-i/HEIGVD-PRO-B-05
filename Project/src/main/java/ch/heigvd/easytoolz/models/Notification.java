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

    public StateNotification getState() {
        return state;
    }

    public void setState(StateNotification state) {
        this.state = state;
    }

    public void setIsRead(boolean read) {
        this.isRead = read;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public Notification() {}

    public Notification(String message, StateNotification state, User recipient){
        this.message = message;
        this.state = state;
        this.isRead = false;
        this.recipient = recipient;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotNull
    private String message;

    @NotNull
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateNotification state;

    @NotNull
    private boolean isRead;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user", referencedColumnName = "userName")
    private User recipient;


}
