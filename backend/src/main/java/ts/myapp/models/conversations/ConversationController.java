package ts.myapp.models.conversations;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ts.myapp.models.conversations.requests.ConversationCreateRequest;
import ts.myapp.models.conversations.requests.ConversationPatchRequest;
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

    @Autowired
    private ConversationRepository conversationRepository;

    @GetMapping("/api/conversations")
    public ApiResponse<List<Conversation>> getConversations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User user = userRepository.findUserByUsername(currentUserName);

        List<Conversation> conversations = user.getConversations();

        return new ApiResponse<>(true, conversations, "Zwrócono konwersacje użytkownika", null);
    }

    @PostMapping("/api/conversations")
    public ApiResponse<Conversation> createConversation(@Valid @RequestBody ConversationCreateRequest conversationCreateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        LocalDateTime currentDate = LocalDateTime.now();

        //Dodaj konwersacje
        User user = userRepository.findUserByUsername(currentUserName);
        Conversation conversation = new Conversation(currentDate, "default", user,  new ArrayList<>());

        user.getConversations().add(conversation);

        List<ts.myapp.models.users.requests.Message> requestMessages = conversationCreateRequest.getMessages();

        for (ts.myapp.models.users.requests.Message requestMessage : requestMessages) {
            Message message =  new Message(requestMessage.getRole(), requestMessage.getContent(), conversation, requestMessage.getDate());
            conversation.addMessage(message);
        }

        userRepository.save(user);

        return new ApiResponse<>(true, conversation, "Udało się stworzyć konwersacje", null);
    }

    @PatchMapping("/api/conversations")
    public ApiResponse<Conversation> updateConversationName(@Valid @RequestBody ConversationPatchRequest conversationUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        // Znajdź użytkownika
        User user = userRepository.findUserByUsername(currentUserName);

        // Znajdź konwersację
        Conversation conversation = conversationRepository.findConversationById(conversationUpdateRequest.getConversationId());
        if (conversation == null || !conversation.getUser_id().getUsername().equals(currentUserName)) {
            return new ApiResponse<>(false, null, "Konwersacja nie istnieje lub nie należy do użytkownika", null);
        }

        // Zaktualizuj nazwę konwersacji
        conversation.setName(conversationUpdateRequest.getName());
        conversationRepository.save(conversation);

        return new ApiResponse<>(true, conversation, "Nazwa konwersacji została zaktualizowana", null);
    }


}
