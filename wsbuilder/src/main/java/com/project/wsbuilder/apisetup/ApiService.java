package com.project.wsbuilder.apisetup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class ApiService {
    private final ApiClient client;

    public ApiService(String baseUrl) {
        this.client = new ApiClient(baseUrl);
    }

    public ApiService(String baseUrl, String apiKey) {
        this.client = new ApiClient(baseUrl, apiKey);
    }

    public String getData(String endpoint) throws Exception {
        HttpURLConnection connection = client.createConnection(endpoint);
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return content.toString();
    }

    public String postData(String requestBody) throws Exception {
        HttpURLConnection connection = client.createConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } else {
            throw new IOException("Server returned HTTP response code: " + responseCode + " for URL: " + connection.getURL());
        }
    }

    public String getDataWithParams(String endpoint, HashMap<String, String> params) throws Exception {
        StringBuilder urlWithParams = new StringBuilder(endpoint);
        if (!params.isEmpty()) {
            urlWithParams.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlWithParams.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            urlWithParams.deleteCharAt(urlWithParams.length() - 1); // Remove the trailing '&'
        }

        HttpURLConnection connection = client.createConnection(urlWithParams.toString());
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return content.toString();
    }
}