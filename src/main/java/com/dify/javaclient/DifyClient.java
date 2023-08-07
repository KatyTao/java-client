package com.dify.javaclient;

import com.dify.javaclient.http.DifyRoute;
import okhttp3.*;
import java.io.IOException;

import static com.dify.javaclient.constants.DifyServerConstants.BASE_URL;
public class DifyClient {

    public static final DifyRoute APPLICATION = new DifyRoute("GET", "/parameters");
    public static final DifyRoute FEEDBACK = new DifyRoute("POST", "/messages/%s/feedbacks");
    public static final DifyRoute CREATE_COMPLETION_MESSAGE = new DifyRoute("POST", "/completion-messages");
    public static final DifyRoute CREATE_CHAT_MESSAGE = new DifyRoute("POST", "/chat-messages");
    public static final DifyRoute GET_CONVERSATION_MESSAGES = new DifyRoute("GET", "/messages");
    public static final DifyRoute GET_CONVERSATIONS = new DifyRoute("GET", "/conversations");
    public static final DifyRoute RENAME_CONVERSATION = new DifyRoute("PATCH", "/conversations/%s");
    public static final DifyRoute DELETE_CONVERSATION = new DifyRoute("DELETE", "/conversations/%s");

    private String apiKey;
    private final String baseUrl;
    private final OkHttpClient client;

    public DifyClient(String apiKey) {
        this(apiKey, BASE_URL);
    }

    public DifyClient(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.client = new OkHttpClient();
    }

    public void updateApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Response sendRequest(DifyRoute route, String[] formatArgs, RequestBody body) throws IOException {
        String formattedURL = (formatArgs != null && formatArgs.length > 0)
                ? String.format(route.url, (Object[]) formatArgs)
                : route.url;

        Request request = new Request.Builder()
                .url(baseUrl + formattedURL)
                .method(route.method, body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        return client.newCall(request).execute();
    }

    public Response messageFeedback(String messageId, String rating, String user) throws IOException {
        String payload = "{\"rating\":\"" + rating + "\", \"user\":\"" + user + "\"}";
        RequestBody body = RequestBody.create(payload, MediaType.parse("application/json"));

        return sendRequest(FEEDBACK, new String[] { messageId }, body);
    }

    public Response getApplicationParameters(String user) throws IOException {
        String payload = "{\"user\":\"" + user + "\"}";
        RequestBody body = RequestBody.create(payload, MediaType.parse("application/json"));

        return sendRequest(APPLICATION, null, body);
    }
}
