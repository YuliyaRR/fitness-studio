package by.it_academy.fitnessstudio.converters;

import by.it_academy.fitnessstudio.core.dto.IngredientCalculated;
import by.it_academy.fitnessstudio.core.dto.product.Product;
import by.it_academy.fitnessstudio.entity.IngredientEntity;
import by.it_academy.fitnessstudio.entity.ProductEntity;
import org.apache.commons.math3.util.Precision;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientEntityToIngredientCalculatedConverter implements Converter<IngredientEntity, IngredientCalculated> {

    private final ProductEntityToDtoConverter productEntityToDtoConverter;

    public IngredientEntityToIngredientCalculatedConverter(ProductEntityToDtoConverter productEntityToDtoConverter) {
        this.productEntityToDtoConverter = productEntityToDtoConverter;
    }

    @Override
    public IngredientCalculated convert(IngredientEntity source) {

        IngredientCalculated.IngredientCalculatedBuilder ingredientBuilder
                = IngredientCalculated.IngredientCalculatedBuilder.create();

        ProductEntity productEntity = source.getProduct();

        int productWeight = productEntity.getWeight();
        int ingredientWeight = source.getWeight();
        int calIngr = (int) Math.round(productEntity.getCalories() * 1.0 / productWeight * ingredientWeight);
        double protIngr = Precision.round(productEntity.getProteins() / productWeight * ingredientWeight, 2);
        double fatsIngr = Precision.round(productEntity.getFats() / productWeight * ingredientWeight, 2);
        double carbIngr = Precision.round(productEntity.getCarbohydrates() / productWeight * ingredientWeight, 2);

        Product convert = productEntityToDtoConverter.convert(productEntity);

        return ingredientBuilder
                .setProduct(convert)
                .setWeight(ingredientWeight)
                .setCalories(calIngr)
                .setProteins(protIngr)
                .setFats(fatsIngr)
                .setCarbohydrates(carbIngr)
                .build();
    }
}
