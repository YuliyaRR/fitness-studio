package by.it_academy.product.core.dto.recipe;

import by.it_academy.product.converters.LocalDateTimeToLongMillisSerializer;
import by.it_academy.product.core.dto.ingredient.IngredientCalculated;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @EqualsAndHashCode.Include
    @JsonProperty(index = 1)
    private UUID uuid;
    @JsonProperty(value = "dt_create", index = 2)
    @JsonSerialize(using = LocalDateTimeToLongMillisSerializer.class)
    private LocalDateTime dtCreate;
    @EqualsAndHashCode.Include
    @JsonProperty(value = "dt_update", index = 3)
    @JsonSerialize(using = LocalDateTimeToLongMillisSerializer.class)
    private LocalDateTime dtUpdate;
    @EqualsAndHashCode.Include
    private String title;
    private List<IngredientCalculated> composition;
    private Integer weight;
    private Integer calories;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
}
