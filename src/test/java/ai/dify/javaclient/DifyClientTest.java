package ai.dify.javaclient;

import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class DifyClientTest {

    private static final String TEST_API_KEY = "testApiKey";
    private static final String TEST_BASE_URL = "http://testBaseUrl";
    private DifyClient difyClient;

    @Mock
    private OkHttpClient mockClient;

    @Mock
    private Call mockCall;

    @Mock
    private Response mockResponse;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        difyClient = new DifyClient(TEST_API_KEY, TEST_BASE_URL);
        // Use reflection to inject the mock client into the DifyClient
        // Alternatively, consider adding a constructor to accept the OkHttpClient
        try {
            java.lang.reflect.Field clientField = DifyClient.class.getDeclaredField("client");
            clientField.setAccessible(true);
            clientField.set(difyClient, mockClient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetApplicationParameters() throws Exception {
        when(mockResponse.isSuccessful()).thenReturn(true);
        String testUser = "user123";

        difyClient.getApplicationParameters(testUser);

        verify(mockClient).newCall(any(Request.class));
        verify(mockCall).execute();
    }

    @Test
    public void testMessageFeedback() throws Exception {
        when(mockResponse.isSuccessful()).thenReturn(true);

        difyClient.messageFeedback("12345", "good", "user123");

        verify(mockClient).newCall(any(Request.class));
        verify(mockCall).execute();
    }

}
