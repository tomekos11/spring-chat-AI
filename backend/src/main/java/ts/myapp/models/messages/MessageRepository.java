package ts.myapp.models.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ts.myapp.models.conversations.Conversation;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    void deleteByConversation(Conversation conversation);
}