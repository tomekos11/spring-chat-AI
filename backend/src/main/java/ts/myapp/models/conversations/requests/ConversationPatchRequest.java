package ts.myapp.models.conversations.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConversationPatchRequest {
    @NotNull
    private Long conversationId;

    @NotNull
    private String name;
}
