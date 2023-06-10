package by.it_academy.product.web.controllers;

import by.it_academy.product.core.dto.OnePage;
import by.it_academy.product.core.dto.recipe.Recipe;
import by.it_academy.product.core.dto.recipe.RecipeCreate;
import by.it_academy.product.service.api.IRecipeService;
import jakarta.validation.constraints.Past;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/recipe")
public class RecipeController {
    private final IRecipeService recipeService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecipe(@RequestBody RecipeCreate recipeCreate) {
        recipeService.save(recipeCreate);
    }

    @RequestMapping(method = RequestMethod.GET)
    public OnePage<Recipe> getRecipesPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                          @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return recipeService.getRecipesPage(pageable);
    }
    @RequestMapping(path = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT)
    public void updateRecipe(@PathVariable("uuid") UUID uuid,
                             @PathVariable("dt_update") @Past LocalDateTime dtUpdate,
                             @RequestBody RecipeCreate recipeCreate) {
        recipeService.update(uuid, dtUpdate, recipeCreate);
    }

}
