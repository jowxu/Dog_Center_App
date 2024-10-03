package model;

import java.util.stream.Stream;

public interface IACFilterPlanner {

    /**
     * Filters and sorts the list of dogs.
     *
     * @param nameOn      indicates whether to filter by name
     * @param nameFilter  the name to filter by
     * @param sexOn       indicates whether to filter by sex
     * @param sexFilter   the sex to filter by
     * @param breedOn     indicates whether to filter by breed
     * @param breedFilter the breed to filter by
     * @param ageOn       indicates whether to filter by age
     * @param ageFilter   the age to filter by
     * @param weightOn    indicates whether to filter by weight
     * @param weightFilter the weight to filter by
     * @param priceOn     indicates whether to filter by price
     * @param priceFilter the price to filter by
     * @param sortOn      the field to sort by
     * @param ascending   if true, sort in ascending order; otherwise, sort in descending order
     * @return the stream of filtered/sorted dogs
     */
    Stream<Dog> filter(boolean nameOn, String nameFilter,
                       boolean sexOn, String sexFilter,
                       boolean breedOn, String breedFilter,
                       boolean ageOn, String ageFilter,
                       boolean weightOn, String weightFilter,
                       boolean priceOn, String priceFilter,
                       String sortOn, boolean ascending);

    /**
     * Applies a filter to the stream based on the specified field and value.
     *
     * @param filterOn indicates whether to apply the filter
     * @param field    the field to filter by
     * @param value    the value to filter by
     */
    void applyFilter(boolean filterOn, String field, Object value);
}
