package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository  extends JpaRepository<Conversation,Integer> {

    List<Conversation> findByOwnerOrBorrower(String owner, String borrower);
}
