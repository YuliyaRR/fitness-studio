package by.it_academy.fitnessstudio.service.api;

import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.Recipe;
import by.it_academy.fitnessstudio.core.dto.RecipeCreate;

import java.util.UUID;

public interface IRecipeService {

    void save(RecipeCreate recipeCreate);

    OnePage<Recipe> getRecipesPage(Integer page, Integer size);

    void update(UUID uuid, Long dtUpdate, RecipeCreate recipeCreate);
}
