package by.it_academy.fitnessstudio.core.dto.ingredient;

import by.it_academy.fitnessstudio.core.dto.product.Product;

public class IngredientCalculated {
    private Product product;
    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public IngredientCalculated() {
    }

    public IngredientCalculated(Product product, int weight) {
        this.product = product;
        this.weight = weight;
    }

    public IngredientCalculated(Product product, int weight,
                                int calories, double proteins,
                                double fats, double carbohydrates) {
        this.product = product;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public static class IngredientCalculatedBuilder {
        private Product product;
        private int weight;
        private int calories;
        private double proteins;
        private double fats;
        private double carbohydrates;

        private IngredientCalculatedBuilder() {
        }

        public static IngredientCalculatedBuilder create(){
            return new IngredientCalculatedBuilder();
        }

        public IngredientCalculatedBuilder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public IngredientCalculatedBuilder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public IngredientCalculatedBuilder setCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public IngredientCalculatedBuilder setProteins(double proteins) {
            this.proteins = proteins;
            return this;
        }

        public IngredientCalculatedBuilder setFats(double fats) {
            this.fats = fats;
            return this;
        }

        public IngredientCalculatedBuilder setCarbohydrates(double carbohydrates) {
            this.carbohydrates = carbohydrates;
            return this;
        }

        public IngredientCalculated build() {
            return new IngredientCalculated(product, weight, calories, proteins, fats, carbohydrates);
        }
    }
}
