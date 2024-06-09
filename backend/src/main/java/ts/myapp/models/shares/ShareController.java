package ts.myapp.models.shares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.models.conversations.Conversation;
import ts.myapp.models.conversations.ConversationRepository;
import ts.myapp.models.shares.requests.ShareConversationRequest;
import ts.myapp.models.users.User;
import ts.myapp.models.users.UserRepository;
import ts.myapp.services.ApiResponse;
import ts.myapp.services.SlugService;

import java.util.List;

@RestController
public class ShareController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShareRepository shareRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private SlugService slugService;

    @PostMapping("/api/share")
    public ApiResponse<Share> getAllSharesForCurrentUser(@RequestBody ShareConversationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User user = userRepository.findUserByUsername(currentUserName);

        if (user == null) {
            return new ApiResponse<>(true, null, "Użytkownkik nie znaleziony", null);
        }

        // Znajdź konwersację
        Conversation conversation = conversationRepository.findById(request.getConversationId()).orElse(null);

        if (conversation == null || !conversation.getUser().getUsername().equals(currentUserName)) {
            return new ApiResponse<>(false, null, "Konwersacja nie istnieje lub nie należy do użytkownika", null);
        }

        String slug = slugService.generateUniqueSlug(8);

        Share share = new Share(slug, request.getMaintainAnonymity(), conversation, request.getDate(), request.getExpireDate());

        
        shareRepository.save(share);
        return new ApiResponse<>(true, share, "Poprawnie udostępniono", null);
    }

}
