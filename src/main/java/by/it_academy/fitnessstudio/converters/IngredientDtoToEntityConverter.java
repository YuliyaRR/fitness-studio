package by.it_academy.fitnessstudio.converters;

import by.it_academy.fitnessstudio.core.dto.Ingredient;
import by.it_academy.fitnessstudio.entity.IngredientEntity;
import by.it_academy.fitnessstudio.service.api.IProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientDtoToEntityConverter implements Converter<Ingredient, IngredientEntity> {

    private final IProductService productService;

    public IngredientDtoToEntityConverter(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public IngredientEntity convert(Ingredient source) {
        return new IngredientEntity(productService.getEntity(source.getProduct().getUuid()),
                source.getWeight());
    }

}
