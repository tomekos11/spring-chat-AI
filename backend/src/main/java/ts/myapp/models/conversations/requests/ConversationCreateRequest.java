package ts.myapp.models.conversations.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ts.myapp.models.users.requests.Message;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConversationCreateRequest {

    LocalDateTime date;
    List<Message> messages;

}