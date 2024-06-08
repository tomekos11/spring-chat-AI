package ts.myapp.models.conversations.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConversationCreateRequest {

    @NotNull(message = "Nie można wysłać pustego zapytania")
    @Size(min = 2, max = 1000, message="Można wysłać minimalnie 1 znak i maksymalnie 1000")
    public String content;

}