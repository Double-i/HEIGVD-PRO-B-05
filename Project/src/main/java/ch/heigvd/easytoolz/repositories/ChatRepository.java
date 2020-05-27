package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage,Integer> {

    /**
     * permet de trouver une liste de message concernant une conversation et un prêt
     * @param conv
     * @param loan
     * @return
     */
    List<ChatMessage> findByFkConversation_IDAndFkConversation_Loan(int conv,int loan);
}
