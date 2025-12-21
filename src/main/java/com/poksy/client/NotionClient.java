package com.poksy.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.poksy.config.NotionConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * HTTP client wrapper for Notion API calls.
 * Handles authentication and common request/response patterns.
 */
public class NotionClient {
    
    private final HttpClient httpClient;
    
    public NotionClient() {
        this.httpClient = HttpClient.newHttpClient();
    }
    
    /**
     * Creates a new page in a Notion database.
     * 
     * @param jsonBody The JSON body for the page creation request
     * @return Optional containing the created page ID, or empty if failed
     */
    public Optional<String> createPage(String jsonBody) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(NotionConfig.NOTION_API_URL + "/pages"))
                    .header("Authorization", "Bearer " + NotionConfig.NOTION_TOKEN)
                    .header("Notion-Version", NotionConfig.NOTION_VERSION)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                String pageId = json.get("id").getAsString();
                return Optional.of(pageId);
            } else {
                System.err.println("API Error [" + response.statusCode() + "]: " + response.body());
                return Optional.empty();
            }
            
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    /**
     * Queries a database to find pages matching certain criteria.
     * 
     * @param databaseId The database to query
     * @param filterJson The filter criteria in JSON format
     * @return The response body as a string
     */
    public Optional<String> queryDatabase(String databaseId, String filterJson) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(NotionConfig.NOTION_API_URL + "/databases/" + databaseId + "/query"))
                    .header("Authorization", "Bearer " + NotionConfig.NOTION_TOKEN)
                    .header("Notion-Version", NotionConfig.NOTION_VERSION)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(filterJson))
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return Optional.of(response.body());
            } else {
                System.err.println("Query Error [" + response.statusCode() + "]: " + response.body());
                return Optional.empty();
            }
            
        } catch (Exception e) {
            System.err.println("Query failed: " + e.getMessage());
            return Optional.empty();
        }
    }
}
