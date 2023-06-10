package by.it_academy.product.core.dto.ingredient;

import by.it_academy.product.core.dto.product.Product;
import lombok.*;

@Getter
@Setter
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCalculated {
    private Product product;
    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;
}
