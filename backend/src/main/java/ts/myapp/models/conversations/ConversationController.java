package ts.myapp.models.conversations;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.models.conversations.requests.ConversationCreateRequest;
import ts.myapp.models.messages.Message;
import ts.myapp.models.users.User;
import ts.myapp.models.users.UserRepository;
import ts.myapp.services.ApiResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ConversationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/conversations")
    public ApiResponse<List<Conversation>> getConversations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User user = userRepository.findUserByUsername(currentUserName);

        List<Conversation> conversations = user.getConversations();

        return new ApiResponse<>(true, conversations, "Zwrócono konwersacje użytkownika", null);
    }

    @PostMapping("/api/conversations")
    public ApiResponse<List<Conversation>> createConversation(@Valid @RequestBody ConversationCreateRequest conversationCreateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        LocalDateTime currentDate = LocalDateTime.now();

        //Dodaj konwersacje
        User user = userRepository.findUserByUsername(currentUserName);
        Conversation conversation = new Conversation(currentDate, user,  new ArrayList<>());

        user.getConversations().add(conversation);

        //Dodaj wiadomość w nowej konwersacji
        Message message = new Message("user", conversationCreateRequest.getContent(), conversation, currentDate);
        conversation.addMessage(message);

        userRepository.save(user);
        List<Conversation> conversations = user.getConversations();

        return new ApiResponse<>(true, conversations, "Udało się stworzyć konwersacje", null);
    }
}
