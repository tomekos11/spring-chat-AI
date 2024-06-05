package ts.myapp.models.json;

public class jsonRequest {
    private String name;

    // Domyślny konstruktor
    public jsonRequest() {}

    // Konstruktor z parametrem
    public jsonRequest(String name) {
        this.name = name;
    }

    // Getter i setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}