package by.it_academy.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes", schema = "app")
public class RecipeEntity {
    @Id
    @EqualsAndHashCode.Include
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Version
    @Column(name = "dt_update")
    @EqualsAndHashCode.Include
    private LocalDateTime dtUpdate;
    @EqualsAndHashCode.Include
    private String title;
    @ElementCollection
    @CollectionTable(schema = "app", name = "ingredients_recipes",
    joinColumns = @JoinColumn(name = "recipes_uuid"))
    private List<IngredientEntity> composition;
}
