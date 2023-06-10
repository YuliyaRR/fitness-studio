package by.it_academy.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class IngredientEntity {
    @ManyToOne
    @JoinColumn(name = "product_uuid")
    private ProductEntity product;
    @Column
    private int weight;
}
