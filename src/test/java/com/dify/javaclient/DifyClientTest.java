package com.dify.javaclient;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class DifyClientTest {

    @Test
    public void TestConnection() throws IOException {
        DifyClient difyClient = new DifyClient("mock-api-key");
        difyClient.getApplicationParameters("test");
    }
}
