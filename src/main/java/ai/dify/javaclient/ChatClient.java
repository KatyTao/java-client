package ai.dify.javaclient;

import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatClient extends DifyClient {
    public ChatClient(String apiKey) {
        super(apiKey);
    }

    public ChatClient(String apiKey, String baseUrl) {
        super(apiKey, baseUrl);
    }

    private String generateQueryParams(Map<String, String> params) {
        List<String> keyValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            keyValuePairs.add(entry.getKey() + "=" + entry.getValue());
        }
        return String.join("&", keyValuePairs);
    }

    public Response createChatMessage(String inputs, String query, String user, boolean stream, String conversation_id) throws DifyClientException {
        JSONObject json = new JSONObject();
        json.put("inputs", inputs);
        json.put("query", query);
        json.put("user", user);
        json.put("response_mode", stream ? "streaming" : "blocking");
        if (conversation_id != null && !conversation_id.isEmpty()) {
            json.put("conversation_id", conversation_id);
        }

        return sendRequest(CREATE_CHAT_MESSAGE, null, createJsonPayload(json));
    }

    public Response getConversationMessages(String user, String conversation_id, String first_id, int limit) throws DifyClientException {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("user", user);

        if (conversation_id != null) {
            queryParams.put("conversation_id", conversation_id);
        }
        if (first_id != null) {
            queryParams.put("first_id", first_id);
        }
        if (limit > 0) {
            queryParams.put("limit", String.valueOf(limit));
        }
        String formattedQueryParams = generateQueryParams(queryParams);

        return sendRequest(GET_CONVERSATION_MESSAGES, new String[] {formattedQueryParams}, null);
    }

    public Response getConversations(String user, String first_id, int limit, String pinned) throws DifyClientException {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("user", user);
        if (first_id != null && !first_id.isEmpty()) {
            queryParams.put("first_id", first_id);
        }
        if (limit > 0) {
            queryParams.put("limit", String.valueOf(limit));
        }
        if (pinned != null && !pinned.isEmpty()) {
            queryParams.put("pinned", pinned);
        }
        String formattedQueryParams = generateQueryParams(queryParams);
        return sendRequest(GET_CONVERSATIONS, new String[] {formattedQueryParams}, null);
    }

    public Response renameConversation(String conversation_id, String name, String user) throws DifyClientException {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("user", user);

        return sendRequest(RENAME_CONVERSATION, new String[]{conversation_id}, createJsonPayload(json));
    }

    public Response deleteConversation(String conversation_id, String user) throws DifyClientException {
        JSONObject json = new JSONObject();
        json.put("user", user);

        return sendRequest(DELETE_CONVERSATION, new String[]{conversation_id}, createJsonPayload(json));
    }
}

