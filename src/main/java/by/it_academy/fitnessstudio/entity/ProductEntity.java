package by.it_academy.fitnessstudio.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product", schema = "app")
public class ProductEntity {
    @Id
    private UUID uuid;
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
    private LocalDateTime dtUpdate;

    public ProductEntity() {
    }

    public ProductEntity(UUID uuid, String title,
                         int weight, int calories,
                         double proteins, double fats,
                         double carbohydrates, LocalDateTime dtCreate,
                         LocalDateTime dtUpdate) {
        this.uuid = uuid;
        this.title = title;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }

    public ProductEntity(UUID uuid, LocalDateTime dtUpdate) {
        this.uuid = uuid;
        this.dtUpdate = dtUpdate;
    }

    public ProductEntity(UUID uuid) {
        this.uuid = uuid;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return weight == that.weight
                && calories == that.calories
                && Double.compare(that.proteins, proteins) == 0
                && Double.compare(that.fats, fats) == 0 && Double.compare(that.carbohydrates, carbohydrates) == 0
                && Objects.equals(uuid, that.uuid)
                && Objects.equals(title, that.title)
                && Objects.equals(dtCreate, that.dtCreate)
                && Objects.equals(dtUpdate, that.dtUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, title, weight, calories, proteins, fats, carbohydrates, dtCreate, dtUpdate);
    }

    public static class ProductEntityBuilder {
        private UUID uuid;
        private String title;
        private int weight;
        private int calories;
        private double proteins;
        private double fats;
        private double carbohydrates;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;

        private ProductEntityBuilder() {
        }

        public static ProductEntityBuilder create(){
            return new ProductEntityBuilder();
        }

        public ProductEntityBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public ProductEntityBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ProductEntityBuilder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public ProductEntityBuilder setCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public ProductEntityBuilder setProteins(double proteins) {
            this.proteins = proteins;
            return this;
        }

        public ProductEntityBuilder setFats(double fats) {
            this.fats = fats;
            return this;
        }

        public ProductEntityBuilder setCarbohydrates(double carbohydrates) {
            this.carbohydrates = carbohydrates;
            return this;
        }

        public ProductEntityBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public ProductEntityBuilder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public ProductEntity build() {
            return new ProductEntity(uuid, title, weight, calories, proteins, fats, carbohydrates, dtCreate, dtUpdate);
        }
    }
}
