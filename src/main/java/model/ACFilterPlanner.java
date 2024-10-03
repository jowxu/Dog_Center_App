package model;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class ACFilterPlanner implements IACFilterPlanner{
    /** The stream of filtered Dog objects. */
    private Stream<Dog> filtered;
    /** The list of Dog objects to be filtered/sorted. */
    private List<Dog> dogs;

    /**
     * Constructs a new ACFilterPlanner with the given list of dogs.
     *
     * @param dogs the list of Dog objects to be filtered/sorted.
     */
    public ACFilterPlanner(List<Dog> dogs) {
        this.dogs = dogs;
        this.filtered = dogs.stream();
    }

    /**
     * Filters and sorts the list of dogs.
     *
     * @param nameOn indicates whether to filter by name
     * @param nameFilter the name to filter by
     * @param sexOn indicates whether to filter by sex
     * @param sexFilter the sex to filter by
     * @param breedOn indicates whether to filter by breed
     * @param breedFilter the breed to filter by
     * @param ageOn indicates whether to filter by age
     * @param ageFilter the age to filter by
     * @param weightOn indicates whether to filter by weight
     * @param weightFilter the weight to filter by
     * @param priceOn indicates whether to filter by price
     * @param priceFilter the price to filter by
     * @param sortOn the field to sort by
     * @param ascending if true, sort in ascending order; otherwise, sort in descending order
     * @return the stream of filtered/sorted dogs
     */
    @Override
    public Stream<Dog> filter(boolean nameOn, String nameFilter,
                              boolean sexOn, String sexFilter,
                              boolean breedOn, String breedFilter,
                              boolean ageOn, String ageFilter,
                              boolean weightOn, String weightFilter,
                              boolean priceOn, String priceFilter,
                              String sortOn, boolean ascending) {

        filtered = dogs.stream();

        applyFilter(nameOn, "name", nameFilter);
        applyFilter(sexOn, "sex", sexFilter);
        applyFilter(breedOn, "breed", breedFilter);
        applyFilter(ageOn, "age", ageFilter);
        applyFilter(weightOn, "weight", weightFilter);
        applyFilter(priceOn, "price", priceFilter);

        if (sortOn != null && !sortOn.isEmpty()) {
            Comparator<Dog> comparator = ComparatorSet.getComparator(sortOn);
            if (comparator != null) {
                filtered = ascending ? filtered.sorted(comparator) : filtered.sorted(comparator.reversed());
            }
        }

        return filtered;
    }

    /**
     * Applies a filter to the stream based on the specified field and value.
     *
     * @param filterOn indicates whether to apply the filter
     * @param field the field to filter by
     * @param value the value to filter by
     */
    @Override
    public void applyFilter(boolean filterOn, String field, Object value) {
        if (filterOn) {
            if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                throw new IllegalArgumentException(field + " filter cannot be null or empty");
            }
            Predicate<Dog> predicate = ComparatorSet.getPredicate(field, value);
            filtered = filtered.filter(predicate);
        }
    }

}