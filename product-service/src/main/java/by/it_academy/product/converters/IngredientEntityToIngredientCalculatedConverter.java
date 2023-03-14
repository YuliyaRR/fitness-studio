package by.it_academy.product.converters;

import by.it_academy.product.core.dto.ingredient.IngredientCalculated;
import by.it_academy.product.core.dto.product.Product;
import by.it_academy.product.entity.IngredientEntity;
import by.it_academy.product.entity.ProductEntity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        int ingredientWeight = source.getWeight();

        int scale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        BigDecimal coefficient = BigDecimal.valueOf(ingredientWeight).divide(BigDecimal.valueOf(productEntity.getWeight()), scale, roundingMode);

        int calIngr = BigDecimal.valueOf(productEntity.getCalories()).multiply(coefficient).intValue();
        double protIngr = BigDecimal.valueOf(productEntity.getProteins()).multiply(coefficient).setScale(scale, roundingMode).doubleValue();
        double fatsIngr = BigDecimal.valueOf(productEntity.getFats()).multiply(coefficient).setScale(scale, roundingMode).doubleValue();
        double carbIngr = BigDecimal.valueOf(productEntity.getCarbohydrates()).multiply(coefficient).setScale(scale, roundingMode).doubleValue();

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
