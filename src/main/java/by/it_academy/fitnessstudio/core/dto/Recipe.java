package by.it_academy.fitnessstudio.core.dto;

import java.util.List;
import java.util.UUID;

public class Recipe {
    private UUID uuid;
    private Long dtCreate;
    private Long dtUpdate;
    private String title;
    private List<IngredientCalculated> composition;
    private Integer weight;
    private Integer calories;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;

    public Recipe() {
    }

    public Recipe(UUID uuid, Long dtCreate, Long dtUpdate,
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

    public Long getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Long dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Long getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Long dtUpdate) {
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
        private Long dtCreate;
        private Long dtUpdate;
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

        public RecipeBuilder setDtCreate(Long dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public RecipeBuilder setDtUpdate(Long dtUpdate) {
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
