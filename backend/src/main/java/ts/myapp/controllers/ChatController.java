package ts.myapp.controllers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ts.myapp.models.conversations.Conversation;
import ts.myapp.models.conversations.ConversationRepository;
import ts.myapp.models.users.User;
import ts.myapp.models.users.UserRepository;
import ts.myapp.models.users.requests.AskRequest;
import ts.myapp.models.users.requests.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Value("${openAI}")
    private String openAiApiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversationRepository conversationRepository;

    @GetMapping("/test-chatu")
    public ResponseEntity<String> getChatResponse() {
        RestTemplate restTemplate = new RestTemplate();

        // Nagłówki HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.set("Content-Type", "application/json");

        // Tablica wiadomości
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "user").put("content", "Witaj, czy mówisz po polsku?"));
        messages.put(new JSONObject().put("role", "assistant").put("content", "Tak, mówię po polsku."));
        messages.put(new JSONObject().put("role", "user").put("content", "Która jest aktualnie godzina?"));

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");  // lub "gpt-3.5-turbo"
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 20);

        // Utwórz obiekt HttpEntity
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        // Wyślij żądanie POST
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                String.class
        );

        // Zwróć odpowiedź
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.getBody());
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askChat(@RequestBody AskRequest request) {
        System.out.println("doszlo cos na /ask");
        RestTemplate restTemplate = new RestTemplate();

//        Pobieramy usera
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userRepository.findUserByUsername(currentUserName);
//        Tworzymy pustą konwersacje
        Conversation conversation = null;

//        Pobieramy czas
        LocalDateTime currentDate = LocalDateTime.now();

        // Pobieramy listę wiadomości wysłanych przez front (request)
        List<Message> requestMessages = request.getMessages();
        System.out.println(requestMessages);

        // Sprawdzamy konwersacja już jest
        Integer conversationId = request.getConversationId();
        boolean wantToStartConversation = request.isWantToStartConversation();

        System.out.println(conversationId);
        if (conversationId != null) {
            System.out.println("KONTYNUJEMY KONWERSACJE");
            conversation = conversationRepository.findConversationById(conversationId);
        }
        else if (wantToStartConversation) {
            System.out.println("CHCEMY ZROBIC KONWERSACJE");
            conversation = new Conversation(currentDate, user,  new ArrayList<>());
            user.getConversations().add(conversation);
        }

        Message lastRequestMessage = requestMessages.get(requestMessages.size() - 1);
        ts.myapp.models.messages.Message lastMessage =  new ts.myapp.models.messages.Message(lastRequestMessage.getRole(), lastRequestMessage.getContent(), conversation, lastRequestMessage.getDate());
        conversation.addMessage(lastMessage);

        // Tworzymy nową listę wiadomości do przekazania do OpenAI
        JSONArray messages = new JSONArray();
        for (Message message : requestMessages) {
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("role", message.getRole());
            jsonMessage.put("content", message.getContent());
            messages.put(jsonMessage);
        }

        // Nagłówki HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.set("Content-Type", "application/json");

        // Tworzymy nowy obiekt JSON zawierający dane do przekazania do OpenAI
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 20);

        // Tworzymy żądanie HTTP z danymi
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        // Wysyłamy żądanie do OpenAI
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                String.class
        );

        // Dodaj odpowiedź bota do bazy
        JSONObject jsonBotResponse = new JSONObject(response.getBody());

        JSONObject responseMessage = jsonBotResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message");
        String messageContent = responseMessage.getString("content");
        String role = responseMessage.getString("role");

        System.out.println(messageContent);
        System.out.println(role);

        //Dodaj odpowiedź bota w nowej konwersacji
        ts.myapp.models.messages.Message message = new ts.myapp.models.messages.Message(role, messageContent, conversation, currentDate);
        conversation.addMessage(message);

        // Zapisz do bazy
        if (wantToStartConversation || conversationId != null) {
            conversationRepository.save(conversation);
        }

        // Zwracamy odpowiedź od OpenAI
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.getBody());
    }
}
