package ts.myapp.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ts.myapp.models.conversations.Conversation;
import ts.myapp.models.messages.Message;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @Column(name="username")
    private String username;

    @JsonIgnore
    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "user_id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Message> messages;
    private List<Conversation> conversations;

    public User(String username, String password, String role, String name, String surname, String email, String phone) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    public User() {}

}
