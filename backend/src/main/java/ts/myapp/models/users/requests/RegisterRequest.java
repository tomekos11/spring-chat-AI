package ts.myapp.models.users.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotNull(message = "Nazwa użytkownika nie może być pusta")
    @Size(min = 7, max = 20, message="Nazwa użytkownika musi zawierać od 7 do 20 znaków")
    public String username;

    @NotNull(message = "Hasło nie może być puste")
    @Size(min = 7, max = 50, message="Hasło musi zawierać od 7 do 50 znaków")
    public String password;

    @NotNull(message = "Imie nie może być puste")
    @Size(min = 2, max = 30, message="Imie musi zawierać od 2 do 30 znaków")
    public String name;

    @NotNull(message = "Nazwisko nie może być puste")
    @Size(min = 2, max = 50, message="Nazwisko musi zawierać od 2 do 50 znaków")
    public String surname;

    public String email;
    public String phone;


}