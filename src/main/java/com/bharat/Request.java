import java.util.Map;

/**
 * Created by bharat on 2/6/16.
 */
public class Request {

    private Map<String,String> headers;
    private byte[] body;

    public Request(Map<String, String> headers, byte[] body) {
        this.headers = headers;
        this.body = body;
    }
}
