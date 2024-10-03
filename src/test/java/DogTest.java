import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Breed;
import model.Dog;

import static org.junit.jupiter.api.Assertions.*;

public class DogTest {

    private Breed breed;
    private Dog dog;

    @BeforeEach
    public void setUp() {
        Breed breed = new Breed("1", "New Breed", "Description", 10, 12, 50, 70, 45, 60, false);
        dog = new Dog("1", "Dog1", "f", breed, 2, 45.0, "1.png");
    }

    /**
     * Tests the Dog constructor with various invalid inputs.
     * Verifies that IllegalArgumentException is thrown for null or invalid parameters.
     */
    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Dog(null, "Dog2", "f", breed, 5, 20.0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", null, "f", breed, 5, 20.0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", null, breed, 5, 20.0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", null, 5, 20.0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 5, 20.0, null));
    }

    /**
     * Tests the Dog constructor with an invalid sex parameter.
     * Verifies that IllegalArgumentException is thrown for an invalid sex.
     */
    @Test
    public void testConstructorInvalidSex() {
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "x", breed, 5, 20.0, "2.png"));
    }

    /**
     * Tests the Dog constructor with invalid age parameters.
     * Verifies that IllegalArgumentException is thrown for invalid ages.
     */
    @Test
    public void testConstructorInvalidAge() {
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 0, 20.0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, -1, 20.0, "2.png"));
    }

    /**
     * Tests the Dog constructor with invalid weight parameters.
     * Verifies that IllegalArgumentException is thrown for invalid weights.
     */
    @Test
    public void testConstructorInvalidWeight() {
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 5, 0, "2.png"));
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 5, -1, "2.png"));
    }

    /**
     * Tests the Dog constructor with an invalid price parameter.
     * Verifies that IllegalArgumentException is thrown for an invalid price.
     */
    @Test
    public void testConstructorInvalidPrice() {
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 5, 20.0, "2.png", -1, false));
    }

    /**
     * Tests the Dog constructor with an invalid image filename.
     * Verifies that IllegalArgumentException is thrown for an invalid image filename.
     */
    @Test
    public void testConstructorInvalidImage() {
        assertThrows(IllegalArgumentException.class, () -> new Dog("2", "Dog2", "f", breed, 5, 20.0, "invalid.png"));
    }

    /**
     * Tests the getID method of the Dog class.
     * Verifies that the correct ID is returned.
     */
    @Test
    public void testId() {
        assertEquals("1", dog.getID());
    }

    /**
     * Tests the getSex method of the Dog class.
     * Verifies that the correct sex is returned.
     */
    @Test
    public void testGetSex() {
        assertEquals("f", dog.getSex());
    }

    /**
     * Tests the getBreed method of the Dog class.
     * Verifies that the correct breed is returned.
     */
    @Test
    public void testGetBreed() {
        Breed breed = new Breed("1", "New Breed", "Description", 10, 12, 50, 70, 45, 60, false);
        assertEquals(breed, dog.getBreed());
    }

    /**
     * Tests the changeName method of the Dog class.
     * Verifies that the name is correctly updated.
     */
    @Test
    public void testChangeName() {
        dog.changeName("Dog");
        assertEquals("Dog", dog.getName());
    }

    /**
     * Tests the changeAge method of the Dog class with a valid age.
     * Verifies that the age is correctly updated.
     */
    @Test
    public void testChangeAgeValid() {
        dog.changeAge(3);
        assertEquals(3, dog.getAge());
    }

    /**
     * Tests the changeAge method of the Dog class with an invalid age.
     * Verifies that IllegalArgumentException is thrown for an invalid age.
     */
    @Test
    public void testChangeAgeInvalid() {
        assertThrows(IllegalArgumentException.class, () -> dog.changeAge(1));
    }

    /**
     * Tests the changeWeight method of the Dog class with a valid weight.
     * Verifies that the weight is correctly updated.
     */
    @Test
    public void testChangeWeightValid() {
        dog.changeWeight(15.0);
        assertEquals(15.0, dog.getWeight());
    }

    /**
     * Tests the changeWeight method of the Dog class with invalid weights.
     * Verifies that IllegalArgumentException is thrown for invalid weights.
     */
    @Test
    public void testChangeWeightInvalid() {
        assertThrows(IllegalArgumentException.class, () -> dog.changeWeight(0));
        assertThrows(IllegalArgumentException.class, () -> dog.changeWeight(-1));
    }

    /**
     * Tests the changeImage method of the Dog class with a valid image filename.
     * Verifies that the image filename is correctly updated.
     */
    @Test
    public void testChangeImageValid() {
        dog.changeImage("2.png");
        assertEquals("src/main/resources/dogimages/2.png", dog.getImage());
    }

    /**
     * Tests the changeImage method of the Dog class with an invalid image filename.
     * Verifies that IllegalArgumentException is thrown for an invalid image filename.
     */
    @Test
    public void testChangeImageInvalid() {
        assertThrows(IllegalArgumentException.class, () -> dog.changeImage("invalid.png"));
    }

    /**
     * Tests the changePrice method of the Dog class with a valid price.
     * Verifies that the price is correctly updated.
     */
    @Test
    public void testChangePriceValid() {
        dog.changePrice(20.0);
        assertEquals(20.0, dog.getPrice());
    }

    /**
     * Tests the changePrice method of the Dog class with an invalid price.
     * Verifies that IllegalArgumentException is thrown for an invalid price.
     */
    @Test
    public void testChangePriceInvalid() {
        assertThrows(IllegalArgumentException.class, () -> dog.changePrice(-1));
    }

    /**
     * Tests the changeIsReady method of the Dog class.
     * Verifies that the isReady status is correctly updated.
     */
    @Test
    public void testChangeIsReady() {
        dog.changeIsReady(true);
        assertTrue(dog.getIsReady());
        dog.changeIsReady(false);
        assertFalse(dog.getIsReady());
    }
}
