package ts.myapp.models.users.requests;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
public class AskRequest {
    private List<Message> messages;

    @Nullable
    private Long conversationId;

    private boolean wantToStartConversation;
}
