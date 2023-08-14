package ai.dify.javaclient;

/**
 * This exception class represents a general exception that may occur while using the Dify API client.
 * It is used to handle errors related to the Dify API interactions.
 */
public class DifyClientException extends Exception{
    /**
     * Constructs a new DifyClientException with the provided error message.
     *
     * @param message The error message describing the reason for the exception.
     */
    public DifyClientException(String message) {
        super(message);
    }
}
/**
 * This exception class represents an exception that occurs specifically during Dify API request operations.
 * It is used to handle errors related to sending requests to the Dify API.
 */
class DifyRequestException extends DifyClientException {
    /**
     * Constructs a new DifyRequestException with the provided error message.
     *
     * @param message The error message describing the reason for the request exception.
     */
    public DifyRequestException(String message) {
        super(message);
    }
}