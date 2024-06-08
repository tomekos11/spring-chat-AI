package ts.myapp.models.users.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private String role;
    private String content;
    private LocalDateTime date;
}
