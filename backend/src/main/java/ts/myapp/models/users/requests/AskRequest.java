package ts.myapp.models.users.requests;
import java.util.List;

public class AskRequest {
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
