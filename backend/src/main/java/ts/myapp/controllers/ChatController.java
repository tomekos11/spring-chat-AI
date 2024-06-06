package ts.myapp.controllers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/api")
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
        requestBody.put("model", "gpt-4");  // lub "gpt-3.5-turbo"
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
}
