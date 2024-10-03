import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.ACController;
import model.Dog;
import model.AdoptionCenterModel;
import model.Breed;

public class ACControllerTest {

    private ACController controller;
    private Dog dog1;
    private Dog dog2;
    private Dog dog3;

    /**
     * Sets up the testing environment before each test.
     * Creates a new AdoptionCenterModel, breeds, dogs,
     * and initializes the ACController with the model.
     */
    @BeforeEach
    public void setUp() {
        AdoptionCenterModel model = new AdoptionCenterModel();

        Breed breed1 = new Breed("1", "Labrador Retriever", "Friendly and outgoing", 10, 12, 65, 80, 55, 70, false);
        Breed breed2 = new Breed("2", "German Shepherd", "Confident and courageous", 9, 13, 75, 95, 65, 85, false);

        dog1 = new Dog("1", "Max", "M", breed1, 2, 20.0, "1.png", 1000.00, true);
        dog2 = new Dog("2", "Bella", "F", breed2, 4, 30.0, "2.png", 1500.00, true);
        dog3 = new Dog("3", "Charlie", "M", breed1, 1, 25.0, "3.png", 1200.00, true);

        model.addDog(dog1);
        model.addDog(dog2);
        model.addDog(dog3);

        controller = new ACController(model);
    }

    /**
     * Tests the setNameSearch method by searching for a dog by name.
     * Verifies that the correct dog is returned in the search results.
     */
    @Test
    public void testSetNameSearch() {
        controller.setNameSearch("Max");
        controller.setResultList();
        List<Dog> results = controller.getResultList();

        assertEquals(1, results.size());
        assertEquals(dog1, results.get(0));
    }

    /**
     * Tests the setSexSearch method by searching for dogs by sex.
     * Verifies that the correct dogs are returned in the search results.
     */
    @Test
    public void testSetSexSearch() {
        controller.setSexSearch("M");
        controller.setResultList();
        List<Dog> results = controller.getResultList();

        assertEquals(2, results.size());
        assertTrue(results.contains(dog1));
        assertTrue(results.contains(dog3));
    }

    /**
     * Tests the setBreedSearch method by searching for dogs by breed.
     * Verifies that the correct dogs are returned in the search results.
     */
    @Test
    public void testSetBreedSearch() {
        controller.setBreedSearch("Labrador Retriever");
        controller.setResultList();
        List<Dog> results = controller.getResultList();

        assertEquals(2, results.size());
        assertTrue(results.contains(dog1));
        assertTrue(results.contains(dog3));
    }

    /**
     * Tests the clearSearchFilters method by applying filters, clearing them,
     * and checking that all dogs are returned.
     */
    @Test
    public void testClearSearchFilters() {
        controller.setNameSearch("Max");
        controller.setSexSearch("M");
        controller.clearSearchFilters();
        controller.setResultList();
        List<Dog> results = controller.getResultList();

        assertEquals(3, results.size());
    }

    /**
     * Tests the addToWishList method by adding a dog to the wishlist.
     * Verifies that the dog is added and the correct message is returned.
     */
    @Test
    public void testAddToWishList() {
        String result = controller.addToWishList(dog1);
        List<Dog> wishList = controller.getWishList();

        assertEquals(1, wishList.size());
        assertEquals(dog1, wishList.get(0));
        assertEquals("Max has been added to your wishlist.", result);
    }

    /**
     * Tests the addToWishList method by attempting to add a duplicate dog.
     * Verifies that the wishlist size remains the same and the correct message is returned.
     */
    @Test
    public void testAddToWishListDuplicate() {
        controller.addToWishList(dog1);
        String result = controller.addToWishList(dog1);
        List<Dog> wishList = controller.getWishList();

        assertEquals(1, wishList.size());
        assertEquals("Max is already in your wishlist.", result);
    }

    /**
     * Tests the removeFromWishList method by removing a dog from the wishlist.
     * Verifies that the dog is removed and the correct message is returned.
     */
    @Test
    public void testRemoveFromWishList() {
        controller.addToWishList(dog1);
        String result = controller.removeFromWishList(dog1.getID());
        List<Dog> wishList = controller.getWishList();

        assertEquals(0, wishList.size());
        assertEquals("Max was removed from wish list.", result);
    }

    /**
     * Tests the removeFromWishList method by attempting to remove a dog not in the wishlist.
     * Verifies that the correct message is returned.
     */
    @Test
    public void testRemoveFromWishListNotFound() {
        String result = controller.removeFromWishList("999");
        assertEquals("999 could not be found in the wish list.", result);
    }

    /**
     * Tests the setHomeList method to ensure dogs are sorted by age in ascending order.
     * Verifies the order of dogs in the home list.
     */
    @Test
    public void testSortByAgeAscending() {
        controller.setHomeList();
        List<Dog> homeList = controller.getHomeList();

        assertEquals(3, homeList.size());
        assertEquals(dog3, homeList.get(0)); // Youngest dog should be first
        assertEquals(dog1, homeList.get(1));
        assertEquals(dog2, homeList.get(2)); // Oldest dog should be last
    }

    /**
     * Tests the getDog method by retrieving a dog by its ID.
     * Verifies that the correct dog is returned.
     */
    @Test
    public void testGetDog() {
        Dog result = controller.getDog(dog2.getID());
        assertEquals(dog2, result);
    }

    /**
     * Tests the getAdoptableDogs method to ensure all adoptable dogs are returned.
     * Verifies that the list contains the correct dogs.
     */
    @Test
    public void testGetAdoptableDogs() {
        List<Dog> adoptableDogs = controller.getAdoptableDogs();
        assertEquals(3, adoptableDogs.size());
        assertTrue(adoptableDogs.contains(dog1));
        assertTrue(adoptableDogs.contains(dog2));
        assertTrue(adoptableDogs.contains(dog3));
    }

    /**
     * Tests the getWishListToJsonFormat method by adding dogs to the wishlist and checking JSON format.
     * Verifies that the JSON contains the correct dog information.
     */
    @Test
    public void testGetWishListToJsonFormat() {
        controller.addToWishList(dog1);
        controller.addToWishList(dog2);
        String json = controller.getWishListToJsonFormat();

        assertTrue(json.contains("\"name\":\"Max\""));
        assertTrue(json.contains("\"name\":\"Bella\""));
    }

    /**
     * Tests the saveList method by saving the wishlist to a file.
     * Verifies that the file is created and then deletes it after the test.
     */
    @Test
    public void testSaveList() {
        controller.addToWishList(dog1);
        controller.saveList("test_wishlist.json");

        File file = new File("test_wishlist.json");
        assertTrue(file.exists());
        file.delete(); // Cleanup the file after test
    }
}
