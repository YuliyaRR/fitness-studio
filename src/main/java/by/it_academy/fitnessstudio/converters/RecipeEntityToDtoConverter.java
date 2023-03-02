package by.it_academy.fitnessstudio.converters;

import by.it_academy.fitnessstudio.core.dto.Recipe;
import by.it_academy.fitnessstudio.entity.RecipeEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeEntityToDtoConverter implements Converter<RecipeEntity, Recipe> {

    @Override
    public Recipe convert(RecipeEntity source) {
        return null;
    }
}
