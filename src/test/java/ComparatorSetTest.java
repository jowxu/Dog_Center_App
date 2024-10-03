import org.junit.jupiter.api.Test;

import model.AdoptionCenterModel;
import model.Breed;
import model.ComparatorSet;
import model.Dog;

import org.junit.jupiter.api.BeforeEach;

import java.util.Comparator;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class ComparatorSetTest {

    private AdoptionCenterModel model;
    private Dog dog1;
    private Dog dog2;
    private Dog dog3;

    @BeforeEach
    public void setUp() {
        model = new AdoptionCenterModel();
        Breed breed = new Breed("1", "New Breed", "Description", 10, 12, 50, 70, 45, 60, false);
        dog1 = new Dog("1", "Dog1", "f", breed, 2, 45, "1.png");
        dog2 = new Dog("2", "Dog2", "f", breed, 3, 50, "2.png");
        dog3 = new Dog("3", "Dog3", "m", breed, 4, 55, "3.png");
    }

    /**
     * Tests the name comparator retrieval and functionality.
     * Verifies that the comparator correctly orders dogs by name.
     */
    @Test
    public void testGetComparatorName() {
        Comparator<Dog> comparator = ComparatorSet.getComparator("name");
        assertNotNull(comparator);
        assertTrue(comparator.compare(dog1, dog2) < 0);
    }

    /**
     * Tests the sex comparator retrieval and functionality.
     * Verifies that the comparator correctly orders dogs by sex.
     */
    @Test
    public void testGetComparatorSex() {
        Comparator<Dog> comparator = ComparatorSet.getComparator("sex");
        assertNotNull(comparator);
        assertTrue(comparator.compare(dog1, dog3) < 0);
    }

    /**
     * Tests the breed comparator retrieval and functionality.
     * Verifies that the comparator correctly compares dogs by breed.
     */
    @Test
    public void testGetComparatorBreed() {
        Comparator<Dog> comparator = ComparatorSet.getComparator("breed");
        assertNotNull(comparator);
        assertTrue(comparator.compare(dog1, dog2) == 0);
    }

    /**
     * Tests the age comparator retrieval and functionality.
     * Verifies that the comparator correctly orders dogs by age.
     */
    @Test
    public void testGetComparatorAge() {
        Comparator<Dog> comparator = ComparatorSet.getComparator("age");
        assertNotNull(comparator);
        assertTrue(comparator.compare(dog1, dog2) < 0);
    }

    /**
     * Tests the weight comparator retrieval and functionality.
     * Verifies that the comparator correctly orders dogs by weight.
     */
    @Test
    public void testGetComparatorWeight() {
        Comparator<Dog> comparator = ComparatorSet.getComparator("weight");
        assertNotNull(comparator);
        assertTrue(comparator.compare(dog1, dog2) < 0);
    }

    /**
     * Tests the price comparator retrieval and functionality.
     * Verifies that the comparator correctly orders dogs by price.
     */
    @Test
    public void testGetComparatorPrice() {
        model.addDog(dog1);
        model.addDog(dog2);
        model.changeDogPrice(dog1.getID(), 5.0);
        model.changeDogPrice(dog2.getID(), 6.0);
        Comparator<Dog> comparator = ComparatorSet.getComparator("price");
        assertNotNull(comparator);
        assertTrue(comparator.compare(dog1, dog2) < 0);
    }

    /**
     * Tests the retrieval of an invalid comparator.
     * Verifies that null is returned for an invalid comparator type.
     */
    @Test
    public void testGetComparatorInvalid() {
        Comparator<Dog> comparator = ComparatorSet.getComparator("invalid");
        assertNull(comparator);
    }

    /**
     * Tests the name predicate retrieval and functionality.
     * Verifies that the predicate correctly filters dogs by name.
     */
    @Test
    public void testGetPredicateName() {
        Predicate<Dog> predicate = ComparatorSet.getPredicate("name", "Dog1");
        assertNotNull(predicate);
        assertTrue(predicate.test(dog1));
    }

    /**
     * Tests the sex predicate retrieval and functionality.
     * Verifies that the predicate correctly filters dogs by sex.
     */
    @Test
    public void testGetPredicateSex() {
        Predicate<Dog> predicate = ComparatorSet.getPredicate("sex", "f");
        assertNotNull(predicate);
        assertTrue(predicate.test(dog1));
    }

    /**
     * Tests the breed predicate retrieval and functionality.
     * Verifies that the predicate correctly filters dogs by breed.
     */
    @Test
    public void testGetPredicateBreed() {
        Predicate<Dog> predicate = ComparatorSet.getPredicate("breed", "New Breed");
        assertNotNull(predicate);
        assertTrue(predicate.test(dog1));
    }

    /**
     * Tests the age predicate retrieval and functionality.
     * Verifies that the predicate correctly filters dogs by age.
     */
    @Test
    public void testGetPredicateAge() {
        Predicate<Dog> predicate = ComparatorSet.getPredicate("age", 2);
        assertNotNull(predicate);
        assertTrue(predicate.test(dog1));
    }

    /**
     * Tests the weight predicate retrieval and functionality.
     * Verifies that the predicate correctly filters dogs by weight.
     */
    @Test
    public void testGetPredicateWeight() {
        Predicate<Dog> predicate = ComparatorSet.getPredicate("weight", 45.0);
        assertNotNull(predicate);
        assertTrue(predicate.test(dog1));
    }

    /**
     * Tests the price predicate retrieval and functionality.
     * Verifies that the predicate correctly filters dogs by price.
     */
    @Test
    public void testGetPredicatePrice() {
        model.addDog(dog1);
        model.changeDogPrice(dog1.getID(), 5.0);
        Predicate<Dog> predicate = ComparatorSet.getPredicate("price", 5.0);
        assertNotNull(predicate);
        assertTrue(predicate.test(dog1));
    }

    /**
     * Tests the retrieval of an invalid predicate.
     * Verifies that null is returned for an invalid predicate type.
     */
    @Test
    public void testGetPredicateInvalid() {
        Predicate<Dog> predicate = ComparatorSet.getPredicate("invalid", "value");
        assertNull(predicate);
    }
}

