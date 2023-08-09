package ai.dify.javaclient;

import ai.dify.javaclient.constants.DifyServerConstants;
import ai.dify.javaclient.http.DifyRoute;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;

import java.io.IOException;

public class DifyClient {

    public static final DifyRoute APPLICATION = new DifyRoute("GET", "/parameters?user=%s");
    public static final DifyRoute FEEDBACK = new DifyRoute("POST", "/messages/%s/feedbacks");
    public static final DifyRoute CREATE_COMPLETION_MESSAGE = new DifyRoute("POST", "/completion-messages");
    public static final DifyRoute CREATE_CHAT_MESSAGE = new DifyRoute("POST", "/chat-messages");
    public static final DifyRoute GET_CONVERSATION_MESSAGES = new DifyRoute("GET", "/messages?%s");
    public static final DifyRoute GET_CONVERSATIONS = new DifyRoute("GET", "/conversations");
    public static final DifyRoute RENAME_CONVERSATION = new DifyRoute("PATCH", "/conversations/%s");
    public static final DifyRoute DELETE_CONVERSATION = new DifyRoute("DELETE", "/conversations/%s");

    private String apiKey;
    private final String baseUrl;
    private final OkHttpClient client;

    public DifyClient(String apiKey) {
        this(apiKey, DifyServerConstants.BASE_URL);
    }

    public DifyClient(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.client = new OkHttpClient();
    }

    public void updateApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Response sendRequest(DifyRoute route, String[] formatArgs, RequestBody body) throws DifyClientException {
        try {
            String formattedURL = (formatArgs != null && formatArgs.length > 0)
                    ? String.format(route.url, (Object[]) formatArgs)
                    : route.url;

            Request request = new Request.Builder()
                    .url(baseUrl + formattedURL)
                    .method(route.method, body)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new DifyRequestException("Request failed with status: " + response.code());
            }
            return response;
        } catch (IOException e) {
            throw new DifyClientException("Error occurred while sending request: " + e.getMessage());
        }
    }

    public Response messageFeedback(String messageId, String rating, String user) throws DifyClientException {
        JSONObject json = new JSONObject();
        json.put("rating", rating);
        json.put("user", user);

        return sendRequest(FEEDBACK, new String[]{messageId}, createJsonPayload(json));
    }

    public Response getApplicationParameters(String user) throws DifyClientException {
        return sendRequest(APPLICATION, new String[]{user}, null);
    }

    RequestBody createJsonPayload(JSONObject jsonObject) {
        return RequestBody.create(jsonObject.toJSONString(), MediaType.parse("application/json"));
    }
}
