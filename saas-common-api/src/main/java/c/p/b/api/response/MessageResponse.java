package c.p.b.api.response;

/**
 * Created: 2018-08-10 16:41:37
 *
 * @author Michael.Zhang
 */
public class MessageResponse {

    private String message;

    public MessageResponse() {
    }

    public MessageResponse(String message) {
        this.message = message;
    }

    public static MessageResponse build(String message) {
        return new MessageResponse(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
