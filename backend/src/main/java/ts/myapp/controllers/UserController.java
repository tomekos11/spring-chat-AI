package ts.myapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ts.myapp.config.CustomAuthenticationProvider;
import ts.myapp.models.users.User;
import ts.myapp.models.users.UserRepository;
import ts.myapp.models.users.requests.RegisterRequest;
import ts.myapp.services.ApiResponse;
import ts.myapp.validation.Validation;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @PostMapping("/api/auth/register")
    public ApiResponse<Map<String, Object>> registerUser(@Valid @RequestBody RegisterRequest request, BindingResult result) {
        String validationResult = Validation.checkValidation(result);

        if (validationResult != null) {
            return new ApiResponse<>(false, null, "Nie udało się stworzyć konta", validationResult);
        } else if (userRepository.findUserByUsername(request.username) != null) {
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
        return new ApiResponse<>(true, data, "Stworzono nowego użytkownika", null);
    }

    @GetMapping("/api/auth/me")
    public ApiResponse<User> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();


        User user = userRepository.findUserByUsername(currentUserName);

        return new ApiResponse<>(true, user, "Zwrócono dane użytkownika", null);
    }

    @GetMapping("/api/auth/logout")
    public ApiResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return new ApiResponse<>(false, null, "Nie jesteś zalogowany.", null);
        }

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);


        return new ApiResponse<>(true, "Wylogowano pomyślnie", "Użytkownik został wylogowany.", null);
    }

    @GetMapping("/api/usernames")
    public ApiResponse<List<String>> usernames() {

        List<String> usernames = userRepository.findAll().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        return new ApiResponse<>(true, usernames, "Zwrócono nazwy użytkowników", null);
    }
}
