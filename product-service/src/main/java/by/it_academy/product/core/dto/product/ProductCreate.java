package by.it_academy.product.core.dto.product;

import by.it_academy.product.validator.api.ValidString;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ProductCreate {
    @ValidString
    private String title;
    @Positive(message = "Value must be greater than 0")
    private int weight;
    @Positive(message = "Value must be greater than 0")
    private int calories;
    @PositiveOrZero(message = "Value must be 0 or greater")
    private double proteins;
    @PositiveOrZero(message = "Value must be 0 or greater")
    private double fats;
    @PositiveOrZero(message = "Value must be 0 or greater")
    private double carbohydrates;
}
