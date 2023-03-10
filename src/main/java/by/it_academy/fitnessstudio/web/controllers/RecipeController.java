package by.it_academy.fitnessstudio.web.controllers;

import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.recipe.Recipe;
import by.it_academy.fitnessstudio.core.dto.recipe.RecipeCreate;
import by.it_academy.fitnessstudio.service.api.IRecipeService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Validated
@RestController
@RequestMapping(path = "/api/v1/recipe")
public class RecipeController {
    private final IRecipeService recipeService;

    public RecipeController(IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecipe(@RequestBody RecipeCreate recipeCreate) {
        recipeService.save(recipeCreate);
    }

    @RequestMapping(method = RequestMethod.GET)
    public OnePage<Recipe> getRecipesPage(@RequestParam(name = "page", defaultValue = "0") @NotNull Integer page,
                                          @RequestParam(name = "size", defaultValue = "20") @NotNull Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return recipeService.getRecipesPage(pageable);
    }
    @RequestMapping(path = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT)
    public void updateRecipe(@PathVariable("uuid") @NotNull UUID uuid,
                             @PathVariable("dt_update") @NotNull @Past LocalDateTime dtUpdate,
                             @RequestBody RecipeCreate recipeCreate) {
        recipeService.update(uuid, dtUpdate, recipeCreate);
    }

}
