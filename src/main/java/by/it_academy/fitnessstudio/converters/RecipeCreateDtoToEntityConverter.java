package by.it_academy.fitnessstudio.converters;

import by.it_academy.fitnessstudio.core.dto.RecipeCreate;
import by.it_academy.fitnessstudio.entity.IngredientEntity;
import by.it_academy.fitnessstudio.entity.RecipeEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class RecipeCreateDtoToEntityConverter implements Converter<RecipeCreate, RecipeEntity> {
    private final IngredientDtoToEntityConverter ingredientConverter;
    public RecipeCreateDtoToEntityConverter(IngredientDtoToEntityConverter ingredientConverter) {
        this.ingredientConverter = ingredientConverter;
    }

    @Override
    public RecipeEntity convert(RecipeCreate source) {
        List<IngredientEntity> ingredientEntities = source.getComposition().stream()
                .map(ingredient -> ingredientConverter.convert(ingredient)).toList();
        return RecipeEntity.RecipeEntityBuilder.create()
                .setUuid(UUID.randomUUID())
                .setDtCreate(LocalDateTime.now())
                .setDtUpdate(LocalDateTime.now())
                .setTitle(source.getTitle())
                .setComposition(ingredientEntities).build();
    }

   /* private final ConversionService conversionService;

    public RecipeCreateDtoToEntityConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public RecipeEntity convert(RecipeCreate source) {
        List<IngredientEntity> ingredientEntities = source.getComposition().stream()
                .map(ingredient -> conversionService.convert(ingredient, IngredientEntity.class)).toList();
        return RecipeEntity.RecipeEntityBuilder.create()
                .setUuid(UUID.randomUUID())
                .setDtCreate(LocalDateTime.now())
                .setDtUpdate(LocalDateTime.now())
                .setTitle(source.getTitle())
                .setComposition(ingredientEntities).build();;
    }*/
}
