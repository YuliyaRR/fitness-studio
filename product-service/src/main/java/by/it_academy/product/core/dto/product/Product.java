package by.it_academy.product.core.dto.product;

import by.it_academy.product.converters.LocalDateTimeToLongMillisSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
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
    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;
}
