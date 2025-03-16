package com.project.wsbuilder.apisetup;

/**
 * Configuration class for the API client.
 */
public class ApiConfig {
    private final String baseUrl;
    private final String apiKey;

    /**
     * Constructs an ApiConfig with the specified base URL.
     *
     * @param baseUrl the base URL of the API
     */
    public ApiConfig(String baseUrl) {
        this.baseUrl = baseUrl;
        this.apiKey = null;
    }

    public ApiConfig(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    /**
     * Returns the base URL of the API.
     *
     * @return the base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Returns the API key for authentication.
     *
     * @return the API key
     */
    public String getApiKey() {
        return apiKey;
    }
}
