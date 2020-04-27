package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository  extends JpaRepository<Conversation,Integer> {
}
