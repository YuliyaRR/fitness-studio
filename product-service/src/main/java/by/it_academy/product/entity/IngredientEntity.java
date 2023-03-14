package by.it_academy.product.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
public class IngredientEntity {
    @ManyToOne
    @JoinColumn(name = "product_uuid")
    private ProductEntity product;
    @Column
    private int weight;

    public IngredientEntity() {
    }

    public IngredientEntity(ProductEntity product, int weight) {
        this.product = product;
        this.weight = weight;
    }

    public ProductEntity getProduct() {
      return product;
    }

    public void setProduct(ProductEntity product) {
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
        IngredientEntity that = (IngredientEntity) o;
        return weight == that.weight && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, weight);
    }
}
