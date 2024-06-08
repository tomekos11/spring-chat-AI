package ts.myapp.models.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ts.myapp.models.users.User;

@Repository
public interface MessageRepository extends JpaRepository<User, Long> {

}