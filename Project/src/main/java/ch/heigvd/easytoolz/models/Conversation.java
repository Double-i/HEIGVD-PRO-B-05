package ch.heigvd.easytoolz.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)

public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ID;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="sender", referencedColumnName = "userName")
    User sender;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "recipient",referencedColumnName = "userName")
    User recipient;

    @OneToMany(mappedBy = "fkConversation")
    List<ChatMessage> conversation;

    public Conversation(){}
    public Conversation(User sender, User recipient)
    {
        this.sender = sender;
        this.recipient = recipient;
    }
}
