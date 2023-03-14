package by.it_academy.product.converters;

import by.it_academy.product.core.dto.product.ProductCreate;
import by.it_academy.product.entity.ProductEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ProductCreateDtoToEntityConverter implements Converter<ProductCreate, ProductEntity> {
    @Override
    public ProductEntity convert(ProductCreate source) {
        LocalDateTime dtCreate = LocalDateTime.now();

        return ProductEntity.ProductEntityBuilder.create()
                .setUuid(UUID.randomUUID())
                .setDtCreate(dtCreate)
                .setDtUpdate(dtCreate)
                .setTitle(source.getTitle())
                .setWeight(source.getWeight())
                .setCalories(source.getCalories())
                .setProteins(source.getProteins())
                .setFats(source.getFats())
                .setCarbohydrates(source.getCarbohydrates())
                .build();
    }

}
