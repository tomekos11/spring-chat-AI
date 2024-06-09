package ts.myapp.models.shares.requests;


import lombok.Data;
import ts.myapp.models.users.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShareConversationRequest {
    Long conversationId;
    Boolean maintainAnonymity;
    LocalDateTime date;
    LocalDateTime expireDate;
    List<String> usernames;
}
