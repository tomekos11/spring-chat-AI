package ts.myapp.models.conversations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ts.myapp.models.users.User;

@Repository
public interface ConversationRepository extends JpaRepository<User, Long> {

}