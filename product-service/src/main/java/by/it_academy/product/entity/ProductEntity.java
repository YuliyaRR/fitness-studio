package by.it_academy.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product", schema = "app")
public class ProductEntity {

    @Id
    @EqualsAndHashCode.Include
    private UUID uuid;
    @EqualsAndHashCode.Include
    private String title;
    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    @Version
    @EqualsAndHashCode.Include
    private LocalDateTime dtUpdate;
}
