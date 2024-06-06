package ts.myapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ts.myapp.config.CustomAuthenticationProvider;
import ts.myapp.models.users.User;
import ts.myapp.models.users.UserRepository;
import ts.myapp.models.users.requests.LoginRequest;
import ts.myapp.models.users.requests.RegisterRequest;
import ts.myapp.services.ApiResponse;
import ts.myapp.validation.Validation;

import java.util.Map;
import java.util.HashMap;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManagerBuilder authenticationManager;
    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @PostMapping("/api/auth/register")
    public ApiResponse<Map<String, Object>> registerUser(@Valid @RequestBody RegisterRequest request, BindingResult result) {
            String validationResult = Validation.checkValidation(result);

            if (validationResult != null) {
                return new ApiResponse<>(false, null, "Nie udało się stworzyć konta", validationResult);
            }
            else if (userRepository.findUserByUsername(request.username) != null) {
                return new ApiResponse<>(false, null, "Taki użytkownik już istnieje", null);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("username", request.username);

            User newUser = new User(
                    request.username,
                    request.password,
                    "STUDENT",
                    request.name,
                    request.surname,
                    request.email,
                    request.phone
            );
            userRepository.save(newUser);
            return new ApiResponse<>(true, data , "Stworzono nowego użytkownika", null);
    }

    @PostMapping("/api/auth/login")
    public Authentication login(Authentication authentication) {
        System.out.println("XDDD");
        return authenticationProvider.authenticate(authentication);
    }
}
