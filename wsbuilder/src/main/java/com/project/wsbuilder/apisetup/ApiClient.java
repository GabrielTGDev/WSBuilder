package com.project.wsbuilder.apisetup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * ApiClient is responsible for creating HTTP connections to the API.
 */
public class ApiClient {
    private final ApiConfig config;

    /**
     * Constructs an ApiClient with the specified configuration.
     *
     * @param baseUrl the configuration for the API client
     */
    public ApiClient(String baseUrl) {
        this.config = new ApiConfig(baseUrl);
    }

    public ApiClient(String baseUrl, String apiKey) {
        this.config = new ApiConfig(baseUrl, apiKey);
    }

    /**
     * Creates an HTTP connection to the specified endpoint.
     *
     * @param endpoint the API endpoint to connect to
     * @return the HTTP connection to the specified endpoint
     * @throws URISyntaxException if the URI syntax is incorrect
     */
    public HttpURLConnection createConnection(String endpoint) throws IOException, URISyntaxException {
        URL url = new URI(config.getBaseUrl() + endpoint).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", "Bearer " + config.getApiKey());
        return connection;
    }

    public HttpURLConnection createConnection() throws IOException, URISyntaxException {
        URL url = new URI(config.getBaseUrl()).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", "Bearer " + config.getApiKey());
        return connection;
    }

    public String getBaseUrl() {
        return config.getBaseUrl();
    }
}