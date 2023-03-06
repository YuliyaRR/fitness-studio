package by.it_academy.fitnessstudio.core.dto.product;

import by.it_academy.fitnessstudio.converters.LocalDateTimeToLongMillisSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Product {
    @JsonProperty(index = 1)
    private UUID uuid;
    @JsonProperty(value = "dt_create", index = 2)
    @JsonSerialize(using = LocalDateTimeToLongMillisSerializer.class)
    private LocalDateTime dtCreate;
    @JsonProperty(value = "dt_update", index = 3)
    @JsonSerialize(using = LocalDateTimeToLongMillisSerializer.class)
    private LocalDateTime dtUpdate;
    private String title;
    private int weight;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public Product() {
    }

    public Product(UUID uuid) {
        this.uuid = uuid;
    }

    public Product(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate,
                   String title, int weight, int calories,
                   double proteins, double fats, double carbohydrates) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return weight == product.weight
                && calories == product.calories
                && Double.compare(product.proteins, proteins) == 0
                && Double.compare(product.fats, fats) == 0
                && Double.compare(product.carbohydrates, carbohydrates) == 0
                && Objects.equals(uuid, product.uuid)
                && Objects.equals(dtCreate, product.dtCreate)
                && Objects.equals(dtUpdate, product.dtUpdate)
                && Objects.equals(title, product.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, title,
                weight, calories, proteins, fats, carbohydrates);
    }

    public static class ProductBuilder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private String title;
        private int weight;
        private int calories;
        private double proteins;
        private double fats;
        private double carbohydrates;

        private ProductBuilder(){}

        public static ProductBuilder create(){
            return new ProductBuilder();
        }

        public ProductBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public ProductBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public ProductBuilder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate =dtUpdate;
            return this;
        }

        public ProductBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ProductBuilder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public ProductBuilder setCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public ProductBuilder setProteins(double proteins) {
            this.proteins = proteins;
            return this;
        }

        public ProductBuilder setFats(double fats) {
            this.fats = fats;
            return this;
        }

        public ProductBuilder setCarbohydrates(double carbohydrates) {
            this.carbohydrates = carbohydrates;
            return this;
        }

        public Product build(){
            return new Product(uuid, dtCreate, dtUpdate, title, weight, calories, proteins, fats, carbohydrates);
        }
    }
}
