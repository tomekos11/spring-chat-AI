package ts.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ts.myapp.models.json.*;
import ts.myapp.models.table1.Table1;
import ts.myapp.models.table1.Table1Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private Table1Repository table1Repository;

    @RequestMapping(value = "/json", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<jsonResponse> postJson(@RequestBody jsonRequest request) {
        jsonResponse response = new jsonResponse("Hello, " + request.getName() + "!");
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/xd", method = RequestMethod.GET)
    public List<Table1> XD() {
        return table1Repository.findAll();
    }

//    @RequestParam
//    @RequestBody
//    @PathVariable
//    @ModelAttribute

    @RequestMapping(value = "/xd", method = RequestMethod.POST)
    public String XD1(@RequestParam("name") String name) {
        Table1 newRecord = new Table1(name);
        table1Repository.save(newRecord);
        return "Poprawnie zapisano rekord";
    }

    @GetMapping(
            value = "/greetings-with-response-body",
            produces = "application/json"
    )
    public String getGreetingWhileReturnTypeIsString() {
        return "{\"test\": \"Hello\"}";
    }
}

