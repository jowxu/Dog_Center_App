package model;

public interface IDog {

    /**
     * Returns the ID of the dog.
     *
     * @return the ID of the dog
     */
    String getID();

    /**
     * Returns the name of the dog.
     *
     * @return the name of the dog
     */
    String getName();

    /**
     * Returns the sex of the dog.
     *
     * @return the sex of the dog
     */
    String getSex();

    /**
     * Returns the breed of the dog.
     *
     * @return the breed of the dog
     */
    Breed getBreed();

    /**
     * Returns the age of the dog.
     *
     * @return the age of the dog
     */
    int getAge();

    /**
     * Returns the weight of the dog.
     *
     * @return the weight of the dog
     */
    double getWeight();

    /**
     * Returns the image path of the dog.
     * 
     * @return the image path of the dog
     */
    String getImage();

    /**
     * Returns the price of the dog.
     *
     * @return the price of the dog
     */
    double getPrice();

    /**
     * Returns the readiness for adoption status of the dog.
     *
     * @return the readiness for adoption status of the dog
     */
    boolean getIsReady();

    /**
     * Changes the name of the dog.
     *
     * @param name the new name of the dog
     */
    void changeName(String name);

    /**
     * Changes the age of the dog.
     *
     * @param age the new age of the dog
     * @throws IllegalArgumentException if the new age is not greater than the current age
     */
    void changeAge(int age) throws IllegalArgumentException;

    /**
     * Changes the weight of the dog.
     *
     * @param weight the new weight of the dog
     * @throws IllegalArgumentException if the weight is not greater than zero
     */
    void changeWeight(double weight) throws IllegalArgumentException;

    /**
     * Changes the image of the dog.
     *
     * @param image the new image of the dog
     * @throws IllegalArgumentException if the image path is invalid
     */
    void changeImage(String image) throws IllegalArgumentException;

    /**
     * Changes the price of the dog.
     *
     * @param price the new price of the dog
     * @throws IllegalArgumentException if the price is negative
     */
    void changePrice(double price) throws IllegalArgumentException;

    /**
     * Changes the readiness for adoption status of the dog.
     *
     * @param isReady the new readiness for adoption status of the dog
     */
    void changeIsReady(boolean isReady);
}
