package by.it_academy.product.converters;

import by.it_academy.product.core.dto.product.Product;
import by.it_academy.product.entity.ProductEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityToDtoConverter implements Converter<ProductEntity, Product> {
    @Override
    public Product convert(ProductEntity source) {
        return Product.builder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setTitle(source.getTitle())
                .setWeight(source.getWeight())
                .setCalories(source.getCalories())
                .setProteins(source.getProteins())
                .setFats(source.getFats())
                .setCarbohydrates(source.getCarbohydrates())
                .build();
    }
}
