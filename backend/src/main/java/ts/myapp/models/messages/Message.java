package ts.myapp.models.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.models.conversations.Conversation;
import ts.myapp.models.users.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="messages")
public class Message {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="role")
    private String role;

    @Column(name="content")
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conv_id")
    private Conversation conversation;

    @Column(name="date")
    private LocalDateTime date;

    public Message(String role, String content, Conversation conversation, LocalDateTime date) {
        this.role = role;
        this.content = content;
        this.conversation = conversation;
        this.date = date;
    }

}
