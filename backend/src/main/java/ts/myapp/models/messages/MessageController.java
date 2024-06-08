package ts.myapp.models.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.models.users.User;
import ts.myapp.models.users.UserRepository;
import ts.myapp.services.ApiResponse;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/messages")
    public ApiResponse<List<Message>> messages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User user = userRepository.findUserByUsername(currentUserName);

        List<Message> messages = user.getConversations().getFirst().getMessages();

        return new ApiResponse<>(true, messages, "Zwrócono wiadomości użytkownika", null);
    }
}
