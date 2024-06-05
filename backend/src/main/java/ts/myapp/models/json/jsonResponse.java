package ts.myapp.models.json;

public class jsonResponse {
    private String message;

    // Domyślny konstruktor
    public jsonResponse() {
    }

    // Konstruktor z parametrem
    public jsonResponse(String message) {
        this.message = message;
    }

    // Getter i setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}