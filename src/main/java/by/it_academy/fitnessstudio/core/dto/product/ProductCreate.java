package by.it_academy.fitnessstudio.core.dto.product;

import java.util.Objects;

public class ProductCreate {
    private String title;
    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public ProductCreate() {
    }

    public ProductCreate(String title, int weight, int calories, double proteins, double fats, double carbohydrates) {
        this.title = title;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCreate productCreate = (ProductCreate) o;
        return weight == productCreate.weight
                && calories == productCreate.calories
                && Double.compare(productCreate.proteins, proteins) == 0
                && Double.compare(productCreate.fats, fats) == 0
                && Double.compare(productCreate.carbohydrates, carbohydrates) == 0
                && Objects.equals(title, productCreate.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, weight, calories, proteins, fats, carbohydrates);
    }
}
