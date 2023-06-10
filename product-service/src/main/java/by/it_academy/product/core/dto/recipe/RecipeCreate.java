package by.it_academy.product.core.dto.recipe;

import by.it_academy.product.core.dto.ingredient.Ingredient;
import lombok.*;

import java.util.List;
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCreate {
    private String title;
    private List<Ingredient> composition;
}
