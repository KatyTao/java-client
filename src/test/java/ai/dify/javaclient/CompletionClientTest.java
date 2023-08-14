package ai.dify.javaclient;

import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the CompletionClient class, focusing on its methods and interactions.
 */
class CompletionClientTest {

    private static final String TEST_API_KEY = "testApiKey";
    private static final String TEST_BASE_URL = "http://testBaseUrl";
    private CompletionClient completionClient;

    @Mock
    private OkHttpClient mockClient;

    @Mock
    private Call mockCall;

    @Mock
    private Response mockResponse;

    /**
     * Sets up the test environment before each test case.
     *
     * @throws IOException If an I/O error occurs.
     */
    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        completionClient = new CompletionClient(TEST_API_KEY, TEST_BASE_URL);

        // Inject mock OkHttpClient using reflection
        try {
            java.lang.reflect.Field clientField = DifyClient.class.getDeclaredField("client");
            clientField.setAccessible(true);
            clientField.set(completionClient, mockClient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests the createCompletionMessage method of the CompletionClient class.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void testCreateCompletionMessage() throws Exception {
        when(mockResponse.isSuccessful()).thenReturn(true);

        completionClient.createCompletionMessage("testInputs", "testQuery", "testUser", true);

        verify(mockClient).newCall(any(Request.class));
        verify(mockCall).execute();
    }

}
