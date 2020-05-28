package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository  extends JpaRepository<Conversation,Integer> {
    /**
     * @param owner the username of the owner
     * @param borrower the username of the borrower
     * @return sort by owner or borrower
     */
    List<Conversation> findByOwnerOrBorrower(String owner, String borrower);
}
