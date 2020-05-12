package ch.heigvd.easytoolz.models;

import javax.persistence.*;
import java.awt.*;

@Entity
public class ChatMessage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private String sender;
    private String recipient;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_conversation", referencedColumnName = "ID")
    private Conversation fkConversation;



    public int getId() {
        return id;
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

    public void setFkConversation(Conversation fkConversation){this.fkConversation = fkConversation;}
}
