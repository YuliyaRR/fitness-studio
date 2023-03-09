package by.it_academy.fitnessstudio.core.dto.ingredient;

import by.it_academy.fitnessstudio.core.dto.product.Product;

import java.util.Objects;

public class Ingredient {
    private Product product;
    private int weight;

    public Ingredient(Product product, int weight) {
        this.product = product;
        this.weight = weight;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return weight == that.weight && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, weight);
    }
}
