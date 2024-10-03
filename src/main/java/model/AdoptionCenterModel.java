package model;
import java.util.ArrayList;
import java.util.List;

public class AdoptionCenterModel implements IACModel {
    /** List to store Dog objects. */
    private List<Dog> dogs;

    /**
     * Constructor to initialize the list.
     */
    public AdoptionCenterModel() {
        this.dogs = new ArrayList<>();
    }

    /**
     * Adds a Dog to the list.
     *
     * @param dog the Dog object to add
     * @throws IllegalArgumentException if the Dog object is null
     */
    @Override
    public void addDog(Dog dog) {
        if (dog != null) {
            dogs.add(dog);
        } else {
            throw new IllegalArgumentException("Dog object cannot be null.");
        }
    }

    /**
     * Removes a Dog from the list by id.
     *
     * @param dogId id of the Dog to remove
     */
    @Override
    public void removeDog(String dogId) {
        dogs.removeIf(dog -> dog.getID().equals(dogId));
    }

    /**
     * Returns a list of all dogs.
     *
     * @return a list of all Dog objects
     */
    @Override
    public List<Dog> getAllDogs() {
        return new ArrayList<>(dogs);
    }

    /**
     * Gets a dog by its id.
     *
     * @param dogId id of the Dog
     * @return the Dog object with a given id, or null if not found
     */
    @Override
    public Dog getDogById(String dogId) {
        return dogs.stream()
                   .filter(dog -> dog.getID().equals(dogId))
                   .findFirst()
                   .orElse(null);
    }

    /**
     * Changes the age of a Dog by its id.
     *
     * @param dogId id of the Dog
     * @param newAge the new age to set
     * @throws IllegalArgumentException if the Dog with the given id is not found
     */
    @Override
    public void changeDogAge(String dogId, int newAge) {
        Dog dog = getDogById(dogId);
        if (dog != null) {
            dog.changeAge(newAge);
        } else {
            throw new IllegalArgumentException("Dog with given ID not found.");
        }
    }

    /**
     * Changes the price of a Dog by its id.
     *
     * @param dogId id of the Dog
     * @param newPrice the new price to set
     * @throws IllegalArgumentException if the Dog with the given id is not found
     */
    @Override
    public void changeDogPrice(String dogId, double newPrice) {
        Dog dog = getDogById(dogId);
        if (dog != null) {
            dog.changePrice(newPrice);
        } else {
            throw new IllegalArgumentException("Dog with given ID not found.");
        }
    }

    /**
     * Marks a Dog as ready for adoption by its id.
     *
     * @param dogId id of the Dog to update
     * @param isReady true if the Dog is ready for adoption, false otherwise
     * @throws IllegalArgumentException if the Dog does not have a price or if the Dog with the given ID is not found
     */
    @Override
    public void markDogReadyForAdoption(String dogId, boolean isReady) {
        Dog dog = getDogById(dogId);
        if (dog != null) {
            if (dog.getPrice() > 0) {
                dog.changeIsReady(isReady);
            } else {
                throw new IllegalArgumentException("Dog must have a price.");
            }
        } else {
            throw new IllegalArgumentException("Dog with given ID not found.");
        }
    }

    /**
     * Returns a list of all the dogs that are adoptable.
     * @return List of dogs that are adoptable.
     */
    @Override
    public List<Dog> getAdoptableDogs() {
        return this.dogs.stream().filter(dog -> dog.getIsReady() == true).toList();
    }
}
