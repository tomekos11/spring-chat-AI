package ts.myapp.models.conversations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.models.conversations.Conversation;
import ts.myapp.models.messages.Message;
import ts.myapp.models.shares.Share;
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

    @Column(name="name")
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    hidden-relations
    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Share> shares;

    public Conversation(LocalDateTime begin_date, String name, User user, List<Message> messages) {
        this.begin_date = begin_date;
        this.user = user;
        this.messages = messages;
        this.name = name;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

}
