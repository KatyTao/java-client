# Dify Java SDK
This is the Java SDK for the Dify API, which allows you to seamlessly integrate Dify into your Java applications.

## Installation

Please ensure that you have included the necessary dependencies in your project, such as `fastjson2`, `okhttp3`, etc. You can use Maven, Gradle, or any other dependency management tool of your choice. The SDK itself can be included once it is published to a package repository.

For the sake of this README, let's assume the SDK is available on Maven Central:

```xml
<dependency>
    <groupId>com.dify</groupId>
    <artifactId>javaclient</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage
Once the SDK is installed, you can use it in your project as follows:

```java
import com.dify.javaclient.DifyClient;
import com.dify.javaclient.ChatClient;
import com.dify.javaclient.CompletionClient;
import okhttp3.Response;

public class DifyApp {

    private static final String API_KEY = "your-api-key-here";

    public static void main(String[] args) {
        try {
            String user = "random-user-id";
            String inputs = "{\"name\":\"test name a\"}";
            String query = "Please tell me a short story in 10 words or less.";
            boolean responseMode = true;

            // Create a completion client
            CompletionClient completionClient = new CompletionClient(API_KEY);
            Response completionResponse = completionClient.createCompletionMessage(inputs, query, user, responseMode);
            System.out.println(completionResponse.body().string());

            // Create a chat client
            ChatClient chatClient = new ChatClient(API_KEY);
            // Create a chat message
            Response chatResponse = chatClient.createChatMessage(inputs, query, user, true, null);
            System.out.println(chatResponse.body().string());

            // Fetch conversations
            chatClient.getConversations(user);
            // Rename conversation
            String conversationId = "example-conversation-id";
            String name = "new-name";
            chatClient.renameConversation(conversationId, name, user);

            // And so on for other methods...

            DifyClient client = new DifyClient(API_KEY);
            // Fetch application parameters
            client.getApplicationParameters(user);

            // Provide feedback for a message
            String messageId = "your-message-id";
            String rating = "5";
            client.messageFeedback(messageId, rating, user);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

Replace `'your-api-key-here'` with your actual Dify API key.

## License
This SDK is released under the MIT License.
