package by.it_academy.product.core.dto.recipe;

import by.it_academy.product.core.dto.ingredient.Ingredient;

import java.util.List;
import java.util.Objects;

public class RecipeCreate {
    private String title;
    private List<Ingredient> composition;
    public RecipeCreate() {
    }
    public RecipeCreate(String title, List<Ingredient> composition) {
        this.title = title;
        this.composition = composition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Ingredient> getComposition() {
        return composition;
    }

    public void setComposition(List<Ingredient> composition) {
        this.composition = composition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeCreate that = (RecipeCreate) o;
        return Objects.equals(title, that.title) && Objects.equals(composition, that.composition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, composition);
    }
}
