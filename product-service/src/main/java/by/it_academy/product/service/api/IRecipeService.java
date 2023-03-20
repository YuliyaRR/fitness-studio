package by.it_academy.product.service.api;

import by.it_academy.product.core.dto.OnePage;
import by.it_academy.product.core.dto.recipe.Recipe;
import by.it_academy.product.core.dto.recipe.RecipeCreate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IRecipeService {

    UUID save(@NotNull RecipeCreate recipeCreate);

    OnePage<Recipe> getRecipesPage(@NotNull Pageable pageable);

    UUID update(@NotNull UUID uuid, @NotNull @Past LocalDateTime dtUpdate, @NotNull RecipeCreate recipeCreate);
}
