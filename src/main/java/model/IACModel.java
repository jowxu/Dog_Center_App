package model;

import java.util.List;

public interface IACModel {
    /**
     * Adds a Dog to the list.
     *
     * @param dog the Dog object to add
     * @throws IllegalArgumentException if the Dog object is null
     */
    void addDog(Dog dog);

    /**
     * Removes a Dog from the list by id.
     *
     * @param dogId id of the Dog to remove
     */
    void removeDog(String dogId);

    /**
     * Returns a list of all dogs.
     *
     * @return a list of all Dog objects
     */
    List<Dog> getAllDogs();

    /**
     * Gets a dog by its id.
     *
     * @param dogId id of the Dog
     * @return the Dog object with a given id, or null if not found
     */
    Dog getDogById(String dogId);

    /**
     * Changes the age of a Dog by its id.
     *
     * @param dogId id of the Dog
     * @param newAge the new age to set
     * @throws IllegalArgumentException if the Dog with the given id is not found
     */
    void changeDogAge(String dogId, int newAge);

    /**
     * Changes the price of a Dog by its id.
     *
     * @param dogId id of the Dog
     * @param newPrice the new price to set
     * @throws IllegalArgumentException if the Dog with the given id is not found
     */
    void changeDogPrice(String dogId, double newPrice);

    /**
     * Marks a Dog as ready for adoption by its id.
     *
     * @param dogId id of the Dog to update
     * @param isReady true if the Dog is ready for adoption, false otherwise
     * @throws IllegalArgumentException if the Dog does not have a price or if the Dog with the given ID is not found
     */
    void markDogReadyForAdoption(String dogId, boolean isReady);

    /**
     * Returns a list of all the dogs that are adoptable.
     * 
     * @return List of dogs that are adoptable.
     */
    List<Dog> getAdoptableDogs();
}