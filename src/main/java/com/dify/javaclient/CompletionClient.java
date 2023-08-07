package com.dify.javaclient;
import okhttp3.*;
import java.io.IOException;
public class CompletionClient extends DifyClient{
    public CompletionClient(String apiKey) {
        super(apiKey);
    }

    public CompletionClient(String apiKey, String baseUrl) {
        super(apiKey, baseUrl);
    }

    public Response createCompletionMessage(String inputs, String query, String user, boolean stream) throws IOException {
        String response_mode = stream ? "streaming" : "blocking";
        String payload = "{ \"inputs\":\"" + inputs + "\", \"query\":\"" + query + "\", \"user\":\"" + user + "\", \"response_mode\":\"" + response_mode + "\"}";
        RequestBody body = RequestBody.create(payload, MediaType.parse("application/json"));

        return sendRequest(CREATE_COMPLETION_MESSAGE, null, body);
    }
}
