package by.it_academy.fitnessstudio.service.api;

import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.recipe.Recipe;
import by.it_academy.fitnessstudio.core.dto.recipe.RecipeCreate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IRecipeService {

    void save(@NotNull RecipeCreate recipeCreate);

    OnePage<Recipe> getRecipesPage(@NotNull Pageable pageable);

    void update(@NotNull UUID uuid, @NotNull @Past LocalDateTime dtUpdate, @NotNull RecipeCreate recipeCreate);
}
