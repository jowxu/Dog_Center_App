import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ApiUtil;
import model.Breed;

public class ApiUtilTest {
    
    private ApiUtil apiUtil;
    private Map<String, Breed> map;

    @BeforeEach
    public void setUp() {
        apiUtil = new ApiUtil();
    }

    /**
     * Tests parsing of valid breed JSON data.
     * Verifies that the parsed data correctly populates a Breed object with expected values.
     * @throws IOException if there's an error parsing the JSON
     */
    @Test
    public void testParseBreeds() throws IOException {
        String json = "{\"data\":[{\"id\":\"1\",\"attributes\":{\"name\":\"Beagle\",\"description\":\"Friendly\",\"life\":{\"min\":10,\"max\":12},\"male_weight\":{\"min\":25,\"max\":32},\"female_weight\":{\"min\":23,\"max\":30},\"hypoallergenic\":false}}]}";
        map = apiUtil.parseBreeds(json);
        assertNotNull(map);
        assertEquals(1, map.size());
        Breed breed = map.get("Beagle");
        assertNotNull(breed);
        assertEquals("1", breed.id());
        assertEquals("Beagle", breed.name());
        assertEquals("Friendly", breed.description());
        assertEquals(10, breed.lifeMin());
        assertEquals(12, breed.lifeMax());
        assertEquals(25, breed.maleWeightMin());
        assertEquals(32, breed.maleWeightMax());
        assertEquals(23, breed.femaleWeightMin());
        assertEquals(30, breed.femaleWeightMax());
        assertFalse(breed.hypoallergenic());
    }

    /**
     * Tests parsing of empty breed JSON data.
     * Verifies that an empty JSON results in an empty map.
     * @throws IOException if there's an error parsing the JSON
     */
    @Test
    public void testParseBreedsEmpty() throws IOException {
        String empty = "{\"data\":[]}";

        map = apiUtil.parseBreeds(empty);
        assertNotNull(map);
        assertEquals(0, map.size());
    }

    /**
     * Tests parsing of invalid JSON data.
     * Verifies that an IOException is thrown when parsing invalid JSON.
     */
    @Test
    public void testParseBreedsInvalid() {
        String invalid = "test";

        assertThrows(IOException.class, () -> {
            apiUtil.parseBreeds(invalid);
        });
    }

    /**
     * Tests the getBreeds method of ApiUtil.
     * Verifies that the method doesn't throw an exception and returns a non-empty string.
     */
    @Test
    public void testGetBreeds() {
        assertDoesNotThrow(() -> {
            String response = apiUtil.getBreeds();
            assertNotNull(response);
            assertFalse(response.isEmpty());
        });
    }

    /**
     * Tests the entire API workflow.
     * Verifies that breeds can be fetched from the API and parsed correctly,
     * resulting in a map with the expected number of breeds and specific breed data.
     */
    @Test
    public void testApiWorks() {
        try {
            map = apiUtil.parseBreeds(apiUtil.getBreeds());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(map);
        assertEquals(10, map.size());
        assertEquals("Hokkaido", map.get("Hokkaido").name());
    }
}
