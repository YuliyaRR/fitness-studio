package by.it_academy.fitnessstudio.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Entity
@Table(name = "recipes", schema = "app")
public class RecipeEntity {
    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    private String title;
    @ElementCollection
    @CollectionTable(schema = "app", name = "ingredients_recipes",
    joinColumns = @JoinColumn(name = "recipes_uuid"))
    private List<IngredientEntity> composition;

    public RecipeEntity() {
    }

    public RecipeEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate,
                        String title, List<IngredientEntity> composition) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.composition = composition;
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

    public List<IngredientEntity> getComposition() {
        return composition;
    }

    public void setComposition(List<IngredientEntity> composition) {
        this.composition = composition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeEntity entity = (RecipeEntity) o;
        return Objects.equals(uuid, entity.uuid)
                && Objects.equals(dtCreate, entity.dtCreate)
                && Objects.equals(dtUpdate, entity.dtUpdate)
                && Objects.equals(title, entity.title)
                && Objects.equals(composition, entity.composition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, title, composition);
    }

    public static class RecipeEntityBuilder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private String title;
        private List<IngredientEntity> composition;

        private RecipeEntityBuilder() {
        }

        public static RecipeEntityBuilder create() {
            return new RecipeEntityBuilder();
        }

        public RecipeEntityBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public RecipeEntityBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public RecipeEntityBuilder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public RecipeEntityBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public RecipeEntityBuilder setComposition(List<IngredientEntity> composition) {
            this.composition = composition;
            return this;
        }

        public RecipeEntity build() {
            return new RecipeEntity(uuid, dtCreate, dtUpdate, title, composition);
        }

    }
}
