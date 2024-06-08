package ts.myapp.models.conversations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.models.conversations.Conversation;
import ts.myapp.models.messages.Message;
import ts.myapp.models.users.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="conversations")
public class Conversation {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="begin_date")
    private LocalDateTime begin_date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user_id;

    @OneToMany(mappedBy = "conv_id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;

}
