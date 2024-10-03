package model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApiUtil {
    /** The base URL for the Dog API (v2). */
    private static final String API_BASE_URL = "https://dogapi.dog/api/v2";

    /** The HttpClient instance used to send HTTP requests. */
    private final HttpClient httpClient;

    /** The ObjectMapper instance used for JSON parsing. */
    private final ObjectMapper objectMapper;


    /**
     * Constructs a new ApiUtil.
     */
    public ApiUtil() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newBuilder().build();
    }

    /**
     * Fetches a list of dog breeds from the Dog API, including detailed information about each breed.
     *
     * @return A JSON string containing the list of dog breeds with detailed information.
     * @throws IOException If an I/O error occurs when sending or receiving.
     * @throws InterruptedException If the operation is interrupted.
     */
    public String getBreeds() throws IOException, InterruptedException {
        String endpoint = API_BASE_URL + "/breeds";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(endpoint)).GET().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * Parses the JSON response and returns a Map of breed names to Breed objects.
     *
     * @param jsonResponse The JSON string response from the API.
     * @return A Map with breed names as keys and Breed objects as values.
     * @throws IOException If an error occurs during JSON parsing.
     */
    public Map<String, Breed> parseBreeds(String jsonResponse) throws IOException{
        Map<String, Breed> breedMap = new HashMap<>();
        JsonNode root = objectMapper.readTree(jsonResponse);
        JsonNode dataNode = root.get("data");

        if (dataNode != null && dataNode.isArray()) {
            for (JsonNode breedNode : dataNode) {
                JsonNode attributes = breedNode.get("attributes");

                if (attributes != null) {
                    String breedName = attributes.get("name").asText();
                    Breed breed = new Breed (
                            breedNode.get("id").asText(),
                            breedName,
                            attributes.get("description").asText(),
                            attributes.get("life").get("min").asInt(),
                            attributes.get("life").get("max").asInt(),
                            attributes.get("male_weight").get("min").asInt(),
                            attributes.get("male_weight").get("max").asInt(),
                            attributes.get("female_weight").get("min").asInt(),
                            attributes.get("female_weight").get("max").asInt(),
                            attributes.get("hypoallergenic").asBoolean()
                            );
                    breedMap.put(breedName, breed);
                }
            }
        }
        return breedMap;
    }
}