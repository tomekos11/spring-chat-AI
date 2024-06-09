package ts.myapp.models.shares;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ts.myapp.models.conversations.Conversation;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
    @Query("SELECT c FROM Conversation c WHERE c.id = :id")
    Conversation findConversationById(@Param("id") long id);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Share s WHERE s.slug = :slug")
    boolean existsBySlug(String slug);
}