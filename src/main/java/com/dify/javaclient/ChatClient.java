package com.dify.javaclient;

import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;

public class ChatClient extends DifyClient {
    public ChatClient(String apiKey) {
        super(apiKey);
    }

    public ChatClient(String apiKey, String baseUrl) {
        super(apiKey, baseUrl);
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

    public Response getConversationMessages(String user, String conversation_id, String first_id, String limit) throws DifyClientException {
        JSONObject json = new JSONObject();
        json.put("user", user);
        if (conversation_id != null && !conversation_id.isEmpty()) {
            json.put("conversation_id", conversation_id);
        }
        if (first_id != null && !first_id.isEmpty()) {
            json.put("first_id", first_id);
        }
        if (limit != null && !limit.isEmpty()) {
            json.put("limit", limit);
        }

        return sendRequest(GET_CONVERSATION_MESSAGES, null, createJsonPayload(json));
    }

    public Response getConversations(String user, String first_id, String limit, String pinned) throws DifyClientException {
        JSONObject json = new JSONObject();
        json.put("user", user);
        if (first_id != null && !first_id.isEmpty()) {
            json.put("first_id", first_id);
        }
        if (limit != null && !limit.isEmpty()) {
            json.put("limit", limit);
        }
        if (pinned != null && !pinned.isEmpty()) {
            json.put("pinned", pinned);
        }

        return sendRequest(GET_CONVERSATIONS, null, createJsonPayload(json));
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

