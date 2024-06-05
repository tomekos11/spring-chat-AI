package ts.myapp.models.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
//import ts.myapp.models.users.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="username")
    private String username;

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

    public String getPassword() {
        return this.password;
    }

}
