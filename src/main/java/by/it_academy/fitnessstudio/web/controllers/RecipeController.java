package by.it_academy.fitnessstudio.web.controllers;

import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.Recipe;
import by.it_academy.fitnessstudio.core.dto.RecipeCreate;
import by.it_academy.fitnessstudio.service.api.IRecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public OnePage<Recipe> getRecipesPage(@RequestParam(name = "page") Integer page,
                                          @RequestParam(name = "size") Integer size) {
        //валидация параметров: здесь или на сервисе
        return recipeService.getRecipesPage(page, size);
    }
    @RequestMapping(path = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT)
    public void updateRecipe(@PathVariable("uuid") UUID uuid,
                             @PathVariable("dt_update") Long dtUpdate,
                             @RequestBody RecipeCreate recipeCreate) {
        recipeService.update(uuid, dtUpdate, recipeCreate);
    }

}
