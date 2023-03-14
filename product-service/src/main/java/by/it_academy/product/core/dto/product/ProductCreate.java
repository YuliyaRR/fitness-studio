package by.it_academy.product.core.dto.product;

import by.it_academy.product.validator.api.ValidString;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

public class ProductCreate {
    @ValidString
    private String title;
    @Positive(message = "Value must be greater than 0")
    private int weight;
    @Positive(message = "Value must be greater than 0")
    private int calories;
    @PositiveOrZero(message = "Value must be 0 or greater")
    private double proteins;
    @PositiveOrZero(message = "Value must be 0 or greater")
    private double fats;
    @PositiveOrZero(message = "Value must be 0 or greater")
    private double carbohydrates;

    public ProductCreate() {
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
