package model;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents a dog breed with its characteristics.
 * This record encapsulates various attributes of a dog breed, including
 * identification, physical characteristics, and hypoallergenic status.
 *
 * @param id The unique identifier for the breed.
 * @param name The name of the breed.
 * @param description A brief description of the breed's characteristics.
 * @param lifeMin The minimum life expectancy of the breed in years.
 * @param lifeMax The maximum life expectancy of the breed in years.
 * @param maleWeightMin The minimum weight of male dogs of this breed in pounds.
 * @param maleWeightMax The maximum weight of male dogs of this breed in pounds.
 * @param femaleWeightMin The minimum weight of female dogs of this breed in pounds.
 * @param femaleWeightMax The maximum weight of female dogs of this breed in pounds.
 * @param hypoallergenic Indicates whether the breed is considered hypoallergenic.
 */
public record Breed (

    String id,
    String name,
    String description,
    @JsonProperty("life_min") int lifeMin,
    @JsonProperty("life_max") int lifeMax,
    @JsonProperty("male_weight_min") int maleWeightMin,
    @JsonProperty("male_weight_max") int maleWeightMax,
    @JsonProperty("female_weight_min") int femaleWeightMin,
    @JsonProperty("female_weight_max") int femaleWeightMax,
    boolean hypoallergenic
) {}

