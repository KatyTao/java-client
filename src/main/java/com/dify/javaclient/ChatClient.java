package com.dify.javaclient;
import okhttp3.*;
import java.io.IOException;
public class ChatClient extends DifyClient {
    public ChatClient(String apiKey) {
        super(apiKey);
    }

    public ChatClient(String apiKey, String baseUrl) {
        super(apiKey, baseUrl);
    }

    public Response createChatMessage(String inputs, String query, String user, boolean stream, String conversation_id) throws IOException {
        String payload = "{ \"inputs\":\"" + inputs + "\", \"query\":\"" + query + "\", \"user\":\"" + user + "\",";
        payload += "\"response_mode\":\"" + (stream ? "streaming" : "blocking") + "\",";

        if (conversation_id != null && !conversation_id.isEmpty())
            payload += "\"conversation_id\":\"" + conversation_id + "\",";

        payload = payload.substring(0, payload.length()-1) + "}";  // Remove trailing comma and close JSON object
        RequestBody body = RequestBody.create(payload, MediaType.parse("application/json"));

        return sendRequest(CREATE_CHAT_MESSAGE, null, body);
    }

    public Response getConversationMessages(String user, String conversation_id, String first_id, String limit) throws IOException {
        String payload = "{ \"user\":\"" + user + "\",";
        if (conversation_id != null && !conversation_id.isEmpty())
            payload += "\"conversation_id\":\"" + conversation_id + "\",";
        if (first_id != null && !first_id.isEmpty())
            payload += "\"first_id\":\"" + first_id + "\",";
        if (limit != null && !limit.isEmpty())
            payload += "\"limit\":\"" + limit + "\",";

        payload = payload.substring(0, payload.length()-1) + "}";  // Remove trailing comma and close JSON object

        RequestBody body = RequestBody.create(payload, MediaType.parse("application/json"));

        return sendRequest(GET_CONVERSATION_MESSAGES, null, body);
    }

    public Response getConversations(String user, String first_id, String limit, String pinned) throws IOException {
        String payload = "{ \"user\":\"" + user + "\",";
        if (first_id != null && !first_id.isEmpty())
            payload += "\"first_id\":\"" + first_id + "\",";
        if (limit != null && !limit.isEmpty())
            payload += "\"limit\":\"" + limit + "\",";
        if (pinned != null && !pinned.isEmpty())
            payload += "\"pinned\":\"" + pinned + "\",";

        payload = payload.substring(0, payload.length()-1) + "}";  // Remove trailing comma and close JSON object
        RequestBody body = RequestBody.create(payload, MediaType.parse("application/json"));

        return sendRequest(GET_CONVERSATIONS, null, body);
    }

    public Response renameConversation(String conversation_id, String name, String user) throws IOException {
        String payload = "{ \"name\":\"" + name + "\", \"user\":\"" + user + "\" }";
        RequestBody body = RequestBody.create(payload, MediaType.parse("application/json"));

        return sendRequest(RENAME_CONVERSATION, new String[] { conversation_id }, body);
    }

    public Response deleteConversation(String conversation_id, String user) throws IOException {
        String payload = "{ \"user\":\"" + user + "\" }";
        RequestBody body = RequestBody.create(payload, MediaType.parse("application/json"));

        return sendRequest(DELETE_CONVERSATION, new String[] { conversation_id }, body);
    }
}

