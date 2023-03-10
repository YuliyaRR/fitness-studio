package by.it_academy.fitnessstudio.validator;

import by.it_academy.fitnessstudio.core.dto.ingredient.Ingredient;
import by.it_academy.fitnessstudio.core.dto.recipe.RecipeCreate;
import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;
import by.it_academy.fitnessstudio.core.dto.product.Product;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.validator.api.IValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class RecipeCreateValidator implements IValidator<RecipeCreate> {
    @Override
    public void validate(RecipeCreate item) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);
        String title = item.getTitle();

        if(title == null || title.isEmpty() || title.isBlank()) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Title not entered", "title"));
        }

        List<Ingredient> composition = item.getComposition();

        if (composition == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Composition not entered", "composition"));
            throw multiException;
        }

        for (Ingredient ingredient : composition) {
            if (ingredient == null) {
                multiException.addSuppressed(new InvalidInputServiceMultiException("Ingredient not entered", "ingredient"));
            } else {
                Product product = ingredient.getProduct();
                if(product == null) {
                    multiException.addSuppressed(new InvalidInputServiceMultiException("Product not entered", "product"));
                } else {
                    UUID uuid = product.getUuid();
                    if(uuid == null) {
                        multiException.addSuppressed(new InvalidInputServiceMultiException("UUID not entered", "uuid"));
                    }
                }
                int weight = ingredient.getWeight();
                if(weight <= 0) {
                    multiException.addSuppressed(new InvalidInputServiceMultiException("Value must be greater than 0", "weight"));
                }
            }
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }
    }
}
