package com.orangehrm.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpStatusChecker {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NEVER)
            .build();

    public static int getStatusCode(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<Void> response = CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
            ExtentLogger.pass("Sent GET request to " + url + " -> status " + response.statusCode());
            return response.statusCode();
        } catch (IOException | InterruptedException e) {
            ExtentLogger.fail("GET request failed for " + url + " : " + e.getMessage());
            throw new RuntimeException("GET request failed for " + url, e);
        }
    }
}
