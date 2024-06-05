package ts.myapp.models.users.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotNull(message = "Username -> can't be null")
    @Size(min = 7, max = 20, message="Username -> Min:7 characters, Max:20 characters")
    public String username;

    @NotNull(message = "Password -> can't be null")
    @Size(min = 7, max = 50, message="Password -> Min:7 characters, Max:50 characters")
    public String password;

    @NotNull(message = "Name -> can't be null")
    @Size(min = 2, max = 30, message="Name -> Min:2 characters, Max:30 characters")
    public String name;

    @NotNull(message = "Surname -> can't be null")
    @Size(min = 2, max = 50, message="Surname -> Min:2 characters, Max:50 characters")
    public String surname;

    public String email;
    public String phone;


}