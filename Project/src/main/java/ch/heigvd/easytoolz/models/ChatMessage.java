package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import java.awt.*;

@Entity
public class ChatMessage {


    @Id
    private int id;
    private Messagetype type;
    private String content;
    private String sender;
    private String recipient;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_conversation", referencedColumnName = "ID")
    private Conversation fkConversation;


    public enum Messagetype{
        CHAT,
        JOIN,
        LEAVE
    }

    public int getId() {
        return id;
    }

    public Messagetype getType() {
        return type;
    }

    public void setType(Messagetype type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
