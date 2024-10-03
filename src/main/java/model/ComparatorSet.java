package model;

import java.util.*;
import java.util.function.*;

/**
 * A utility class providing methods to create comparators and predicates for filtering and sorting
 * a collection of Dog objects based on different attributes.
 */
public class ComparatorSet {

    /**
     * Returns a Comparator for Dog objects based on the specified attribute.
     * 
     * @param sortOn the attribute to sort by. Supported values are "name", "sex", "breed", "age", "weight", and "price".
     * @return a Comparator that can be used to sort Dog objects, or {@code null} if the
     *         attribute is not recognized.
     */
    public static Comparator<Dog> getComparator(String sortOn) {
        switch (sortOn.toLowerCase()) {
            case "name":
                return Comparator.comparing(Dog::getName);
            case "sex":
                return Comparator.comparing(Dog::getSex);
            case "breed":
                return Comparator.comparing(dog -> dog.getBreed().name());
            case "age":
                return Comparator.comparingInt(Dog::getAge);
            case "weight":
                return Comparator.comparingDouble(Dog::getWeight);
            case "price":
                return Comparator.comparingDouble(Dog::getPrice);
            default:
                return null;
        }
    }

    /**
     * Returns a Predicate for Dog objects based on the specified filter attribute and value.
     * 
     * @param filter the attribute to filter by. Supported values are "name", "sex", "breed", "age", "weight", and "price".
     * @param value the value to compare against the attribute. The type of this value must match the type
     *              expected for the given filter attribute.
     * @return a Predicate that can be used to filter Dog objects, or {@code null} if the
     *         filter attribute is not recognized.
     */
    public static Predicate<Dog> getPredicate(String filter, Object value) {
        switch (filter.toLowerCase()) {
            case "name":
                return dog -> dog.getName().contains((String) value);
            case "sex":
                return dog -> dog.getSex().equalsIgnoreCase((String) value);
            case "breed":
                return dog -> dog.getBreed().name().contains((String) value);
            case "age":
                return dog -> dog.getAge() == (int) value;
            case "weight":
                return dog -> dog.getWeight() == (double) value;
            case "price":
                return dog -> dog.getPrice() == (double) value;
            default:
                return null;
        }
    }
}
