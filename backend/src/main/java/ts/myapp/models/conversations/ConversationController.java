package ts.myapp.models.conversations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.models.messages.Message;
import ts.myapp.models.users.User;
import ts.myapp.models.users.UserRepository;
import ts.myapp.services.ApiResponse;

import java.util.List;

@RestController
public class ConversationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/conversations")
    public ApiResponse<List<Conversation>> conversations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User user = userRepository.findUserByUsername(currentUserName);

        List<Conversation> conversations = user.getConversations();

        return new ApiResponse<>(true, conversations, "Zwrócono konwersacje użytkownika", null);
    }
}
