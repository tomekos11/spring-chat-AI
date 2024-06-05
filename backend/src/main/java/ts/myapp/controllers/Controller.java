package ts.myapp.controllers;

import org.springframework.web.bind.annotation.*;
@RestController
public class Controller {

    @GetMapping("/")
    public String home() {
        return "<html><body>Odpowiedź serwera dla adresu: http://localhost:8080 </br>" + "<h1>@GetMapping(\"/\")</h1></body></html>";
    }

    @GetMapping("/page")
    public String page() {
        return "<html><body>Odpowiedź serwera dla adresu: http://localhost:8080/page </br>" + "<h1>@GetMapping(\"/page\")</h1></body></html>";
    }

}