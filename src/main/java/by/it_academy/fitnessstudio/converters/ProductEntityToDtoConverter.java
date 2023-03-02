package by.it_academy.fitnessstudio.converters;

import by.it_academy.fitnessstudio.core.dto.product.Product;
import by.it_academy.fitnessstudio.entity.ProductEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityToDtoConverter implements Converter<ProductEntity, Product> {
    private final LocalDateTimeToLongMillisConverter localDateTimeToLongMillisConverter;

    public ProductEntityToDtoConverter(LocalDateTimeToLongMillisConverter localDateTimeToLongMillisConverter) {
        this.localDateTimeToLongMillisConverter = localDateTimeToLongMillisConverter;
    }

    @Override
    public Product convert(ProductEntity source) {
        Product.ProductBuilder productBuilder = Product.ProductBuilder.create();
        return productBuilder
                .setUuid(source.getUuid())
                .setDtCreate(localDateTimeToLongMillisConverter.convert(source.getDtCreate()))
                .setDtUpdate(localDateTimeToLongMillisConverter.convert(source.getDtUpdate()))
                .setTitle(source.getTitle())
                .setWeight(source.getWeight())
                .setCalories(source.getCalories())
                .setProteins(source.getProteins())
                .setFats(source.getFats())
                .setCarbohydrates(source.getCarbohydrates())
                .build();
    }
}
