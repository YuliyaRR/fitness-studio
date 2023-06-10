package by.it_academy.product.core.dto.ingredient;

import by.it_academy.product.core.dto.product.Product;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Ingredient {
    private Product product;
    private int weight;
}
