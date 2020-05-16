package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage,Integer> {

    List<ChatMessage> findByFkConversation_IDAndFkConversation_Loan(int conv,int loan);
}
