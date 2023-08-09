package ai.dify.javaclient;

import okhttp3.*;
import com.alibaba.fastjson2.JSONObject;

public class CompletionClient extends DifyClient {
    public CompletionClient(String apiKey) {
        super(apiKey);
    }

    public CompletionClient(String apiKey, String baseUrl) {
        super(apiKey, baseUrl);
    }

    public Response createCompletionMessage(String inputs, String query, String user, boolean stream) throws DifyClientException {
        JSONObject json = new JSONObject();
        json.put("inputs", inputs);
        json.put("query", query);
        json.put("user", user);
        json.put("response_mode", stream ? "streaming" : "blocking");

        return sendRequest(CREATE_COMPLETION_MESSAGE, null, createJsonPayload(json));
    }
}
