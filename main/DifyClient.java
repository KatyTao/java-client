import okhttp3.*;

import java.io.IOException;

public class DifyClient {
    public static final String BASE_URL = "https://api.dify.ai/v1";

    private static class Route {
        String method;
        String url;

        public Route(String method, String url) {
            this.method = method;
            this.url = url;
        }
    }

    public static final Route APPLICATION = new Route("GET", "/parameters");
    public static final Route FEEDBACK = new Route("POST", "/messages/%s/feedbacks");
    public static final Route CREATE_COMPLETION_MESSAGE = new Route("POST", "/completion-messages");
    public static final Route CREATE_CHAT_MESSAGE = new Route("POST", "/chat-messages");
    public static final Route GET_CONVERSATION_MESSAGES = new Route("GET", "/messages");
    public static final Route GET_CONVERSATIONS = new Route("GET", "/conversations");
    public static final Route RENAME_CONVERSATION = new Route("PATCH", "/conversations/%s");
    public static final Route DELETE_CONVERSATION = new Route("DELETE", "/conversations/%s");

    private String apiKey;
    private String baseUrl;
    private OkHttpClient client;

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

    public Response sendRequest(Route route, String[] formatArgs, RequestBody body) throws IOException {
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
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), payload);

        return sendRequest(FEEDBACK, new String[] { messageId }, body);
    }

    public Response getApplicationParameters(String user) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + APPLICATION.url).newBuilder();
        urlBuilder.addQueryParameter("user", user);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        return client.newCall(request).execute();
    }

    public Response createCompletionMessage(String inputs, String query, String user, boolean stream)
            throws IOException {
        String responseMode = stream ? "streaming" : "blocking";
        String payload = "{\"inputs\":\"" + inputs + "\", \"query\":\"" + query + "\", \"user\":\"" + user
                + "\", \"response_mode\":\"" + responseMode + "\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), payload);

        return sendRequest(CREATE_COMPLETION_MESSAGE, null, body);
    }

    public Response createChatMessage(String inputs, String query, String user, boolean stream, String conversationId)
            throws IOException {
        String responseMode = stream ? "streaming" : "blocking";
        String payload = "{\"inputs\":\"" + inputs + "\", \"query\":\"" + query + "\", \"user\":\"" + user
                + "\", \"response_mode\":\"" + responseMode + "\"}";
        if (conversationId != null && !conversationId.isEmpty()) {
            payload = payload.substring(0, payload.length() - 1) + ", \"conversation_id\":\"" + conversationId + "\"}";
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), payload);

        return sendRequest(CREATE_CHAT_MESSAGE, null, body);
    }

    public Response getConversationMessages(String user, String conversationId, String firstId, String limit)
            throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + GET_CONVERSATION_MESSAGES.url).newBuilder();
        urlBuilder.addQueryParameter("user", user);
        if (conversationId != null && !conversationId.isEmpty())
            urlBuilder.addQueryParameter("conversation_id", conversationId);
        if (firstId != null)
            urlBuilder.addQueryParameter("first_id", firstId);
        if (limit != null)
            urlBuilder.addQueryParameter("limit", limit);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        return client.newCall(request).execute();
    }

    public Response getConversations(String user, String firstId, String limit, String pinned) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + GET_CONVERSATIONS.url).newBuilder();
        urlBuilder.addQueryParameter("user", user);
        if (firstId != null)
            urlBuilder.addQueryParameter("first_id", firstId);
        if (limit != null)
            urlBuilder.addQueryParameter("limit", limit);
        if (pinned != null)
            urlBuilder.addQueryParameter("pinned", pinned);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        return client.newCall(request).execute();
    }

    public Response renameConversation(String conversationId, String name, String user) throws IOException {
        String payload = "{\"name\":\"" + name + "\", \"user\":\"" + user + "\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), payload);

        return sendRequest(RENAME_CONVERSATION, new String[] { conversationId }, body);
    }

    public Response deleteConversation(String conversationId, String user) throws IOException {
        String payload = "{\"user\":\"" + user + "\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), payload);

        return sendRequest(DELETE_CONVERSATION, new String[] { conversationId }, body);
    }

    public static void main(String[] args) {
        DifyClient api = new DifyClient("YOUR_API_KEY");
        // Test the methods
    }
}
