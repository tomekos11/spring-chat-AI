package ts.myapp.controllers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ts.myapp.models.users.requests.AskRequest;
import ts.myapp.models.users.requests.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Value("${openAI}")
    private String openAiApiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

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

        // Pobieramy listę wiadomości z obiektu Request
        List<Message> requestMessages = request.getMessages();

        // Tworzymy nową listę wiadomości do przekazania do OpenAI
        JSONArray messages = new JSONArray();
        for (Message message : requestMessages) {
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("role", message.getRole());
            jsonMessage.put("content", message.getContent());
            messages.put(jsonMessage);
        }

        System.out.println(messages);

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

        // Zwracamy odpowiedź od OpenAI
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.getBody());
    }
}
