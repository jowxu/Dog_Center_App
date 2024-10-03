package model;
import java.io.File;

public class Dog implements IDog{

    /** the unique identifier of the dog */
    private String id;

    /** the name of the dog */
    private String name;

    /** the sex of the dog ('m' or 'f', case insensitive) */
    private String sex;

    /** the breed of the dog */
    private Breed breed;

    /** the age of the dog in years */
    private int age;

    /** the weight of the dog in pounds */
    private double weight;

    /** the path to the image file of the dog. */
    private String image;

    /** the price of the dog */
    private double price;

    /** the readiness for adoption status of the dog */
    private boolean isReady;
    
    /** the directory for all the dog images */
    private static final String IMAGE_DIRECTORY = "src/main/resources/dogimages/";

      /**
     * Constructs a new Dog with the specified attributes, defaulting price to 0.00 and readiness for adoption to false.
     *
     * @param id     the unique identifier of the dog
     * @param name   the name of the dog
     * @param sex    the sex of the dog ('m' or 'f', case insensitive)
     * @param breed  the breed of the dog
     * @param age    the age of the dog in years
     * @param weight the weight of the dog in pounds
     * @param image   the image path of the dog
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Dog(String id, String name, String sex, Breed breed, int age, double weight, String image) {
        this(id, name, sex, breed, age, weight, image, 0.00, false);
    }

    /**
     * Constructs a new Dog with the specified attributes.
     *
     * @param id      the unique identifier of the dog
     * @param name    the name of the dog
     * @param sex     the sex of the dog ('m' or 'f', case insensitive)
     * @param breed   the breed of the dog
     * @param age     the age of the dog in years
     * @param weight  the weight of the dog in pounds
     * @param image   the image path of the dog
     * @param price   the price of the dog
     * @param isReady the readiness for adoption status of the dog
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Dog(String id, String name, String sex, Breed breed, int age, double weight, String image, double price, boolean isReady) throws IllegalArgumentException {
        if (id == null || name == null || sex == null || breed == null || image == null) {
            throw new IllegalArgumentException("Fields cannot be null.");
        }
        if (!(sex.equalsIgnoreCase("m") || sex.equalsIgnoreCase("f"))) {
            throw new IllegalArgumentException("Sex must be either m or f, case insensitive.");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("Dog age must be greater than zero.");
        }
        File file = new File(IMAGE_DIRECTORY + image);
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("The image does not exist or the path is not valid. Please place image in correct folder.");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Dog weight must be greater than zero.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price of dog cannot be negative.");
        }
        this.id = id;
        this.name = name;
        this.sex = sex.toLowerCase();
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.image = IMAGE_DIRECTORY + image;
        this.price = price;
        this.isReady = isReady;
    }

    /**
     * Returns the ID of the dog.
     *
     * @return the ID of the dog
     */
    @Override
    public String getID() {
        return this.id;
    }

    /**
     * Returns the name of the dog.
     *
     * @return the name of the dog
     */
     @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the sex of the dog.
     *
     * @return the sex of the dog
     */
    @Override
    public String getSex() {
        return this.sex;
    }

    /**
     * Returns the breed of the dog.
     *
     * @return the breed of the dog
     */
    @Override
    public Breed getBreed() {
        return this.breed;
    }

    /**
     * Returns the age of the dog.
     *
     * @return the age of the dog
     */
    @Override
    public int getAge() {
        return this.age;
    }

    /**
     * Returns the weight of the dog.
     *
     * @return the weight of the dog
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Returns the image path of the dog.
     * 
     * @return the image path of the dog
     */
    @Override
    public String getImage() {
        return this.image;
    }

    /**
     * Returns the price of the dog.
     *
     * @return the price of the dog
     */
    @Override
    public double getPrice() {
        return this.price;
    }

    /**
     * Returns the readiness for adoption status of the dog.
     *
     * @return the readiness for adoption status of the dog
     */
    @Override
    public boolean getIsReady() {
        return this.isReady;
    }

    /**
     * Changes the name of the dog.
     *
     * @param name the new name of the dog
     */
    @Override
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * Changes the age of the dog.
     *
     * @param age the new age of the dog
     * @throws IllegalArgumentException if the new age is not greater than the current age
     */
    @Override
    public void changeAge(int age) throws IllegalArgumentException {
        if (age <= this.age) {
            throw new IllegalArgumentException("The new dog age must be older than the currently assigned one.");
        }
        this.age = age;
    }

    /**
     * Changes the weight of the dog.
     *
     * @param weight the new weight of the dog
     * @throws IllegalArgumentException if the weight is not greater than zero
     */
    @Override
    public void changeWeight(double weight) throws IllegalArgumentException {
        if (weight <= 0) {
            throw new IllegalArgumentException("Dog weight must be greater than zero.");
        }
        this.weight = weight;
    }

    /**
     * Changes the image of the dog.
     *
     * @param image the new image of the dog
     * @throws IllegalArgumentException if the image path is invalid
     */
    @Override
    public void changeImage(String image) throws IllegalArgumentException {
        File file = new File(IMAGE_DIRECTORY + image);
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("The image does not exist or the path is not valid. Please place image in correct folder.");
        }
        this.image = IMAGE_DIRECTORY + image;
    }

    /**
     * Changes the price of the dog.
     *
     * @param price the new price of the dog
     * @throws IllegalArgumentException if the price is negative
     */
    @Override
    public void changePrice(double price) throws IllegalArgumentException {
        if (price < 0) {
            throw new IllegalArgumentException("Price of dog cannot be negative.");
        }
        this.price = price;
    }

    /**
     * Changes the readiness for adoption status of the dog.
     *
     * @param isReady the new readiness for adoption status of the dog
     */
    @Override
    public void changeIsReady(boolean isReady) {
        this.isReady = isReady;
    }
}
