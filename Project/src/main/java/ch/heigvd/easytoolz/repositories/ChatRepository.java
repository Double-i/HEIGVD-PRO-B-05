package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage,Integer> {

    /**
     * @param conv id of the conversation
     * @param loan id of the loan
     * @return all messages from a conversation
     */
    List<ChatMessage> findByFkConversation_IDAndFkConversation_Loan(int conv,int loan);
}
