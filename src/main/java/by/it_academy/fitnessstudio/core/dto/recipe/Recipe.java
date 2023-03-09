package by.it_academy.fitnessstudio.core.dto.recipe;

import by.it_academy.fitnessstudio.converters.LocalDateTimeToLongMillisSerializer;
import by.it_academy.fitnessstudio.core.dto.ingredient.IngredientCalculated;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Recipe {
    @JsonProperty(index = 1)
    private UUID uuid;
    @JsonProperty(value = "dt_create", index = 2)
    @JsonSerialize(using = LocalDateTimeToLongMillisSerializer.class)
    private LocalDateTime dtCreate;
    @JsonProperty(value = "dt_update", index = 3)
    @JsonSerialize(using = LocalDateTimeToLongMillisSerializer.class)
    private LocalDateTime dtUpdate;
    private String title;
    private List<IngredientCalculated> composition;
    private Integer weight;
    private Integer calories;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;

    public Recipe() {
    }

    public Recipe(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate,
                  String title, List<IngredientCalculated> composition,
                  Integer weight, Integer calories,
                  Double proteins, Double fats, Double carbohydrates) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.composition = composition;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<IngredientCalculated> getComposition() {
        return composition;
    }

    public void setComposition(List<IngredientCalculated> composition) {
        this.composition = composition;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Double getProteins() {
        return proteins;
    }

    public void setProteins(Double proteins) {
        this.proteins = proteins;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public static class RecipeBuilder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private String title;
        private List<IngredientCalculated> composition;
        private Integer weight;
        private Integer calories;
        private Double proteins;
        private Double fats;
        private Double carbohydrates;

        private RecipeBuilder() {
        }

        public static RecipeBuilder create() {
            return new RecipeBuilder();
        }

        public RecipeBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public RecipeBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public RecipeBuilder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public RecipeBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public RecipeBuilder setComposition(List<IngredientCalculated> composition) {
            this.composition = composition;
            return this;
        }

        public RecipeBuilder setWeight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public RecipeBuilder setCalories(Integer calories) {
            this.calories = calories;
            return this;
        }

        public RecipeBuilder setProteins(Double proteins) {
            this.proteins = proteins;
            return this;
        }

        public RecipeBuilder setFats(Double fats) {
            this.fats = fats;
            return this;
        }

        public RecipeBuilder setCarbohydrates(Double carbohydrates) {
            this.carbohydrates = carbohydrates;
            return this;
        }

        public Recipe build(){
            return new Recipe(uuid, dtCreate, dtUpdate, title, composition, weight, calories, proteins, fats, carbohydrates);
        }
    }
}
