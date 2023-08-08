package com.dify.javaclient;

public class DifyClientException extends Exception{
    public DifyClientException(String message) {
        super(message);
    }
}

class DifyRequestException extends DifyClientException {
    public DifyRequestException(String message) {
        super(message);
    }
}