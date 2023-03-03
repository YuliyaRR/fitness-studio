package by.it_academy.fitnessstudio.validator;

import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;
import by.it_academy.fitnessstudio.core.dto.product.ProductCreate;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.validator.api.IValidator;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateValidator implements IValidator<ProductCreate> {
    @Override
    public void validate(ProductCreate item) {
        InvalidInputServiceMultiException exception = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);

        String title = item.getTitle();
        if(title == null || title.isBlank() || title.isEmpty()){
            exception.addSuppressed(new InvalidInputServiceMultiException("Title not entered", "title"));
        }

        int weight = item.getWeight();
        if(weight <= 0) {
            exception.addSuppressed(new InvalidInputServiceMultiException("Incorrect weight", "weight"));
        }

        int calories = item.getCalories();
        if(calories < 0) {
            exception.addSuppressed(new InvalidInputServiceMultiException("Value is less than zero", "calories"));
        }

        double proteins = item.getProteins();
        if(proteins < 0) {
            exception.addSuppressed(new InvalidInputServiceMultiException("Value is less than zero", "proteins"));
        }

        double fats = item.getFats();
        if(fats < 0) {
            exception.addSuppressed(new InvalidInputServiceMultiException("Value is less than zero", "fats"));
        }

        double carbohydrates = item.getCarbohydrates();
        if(carbohydrates < 0) {
            exception.addSuppressed(new InvalidInputServiceMultiException("Value is less than zero", "carbohydrates"));
        }

        if(exception.getSuppressed().length != 0) {
            throw exception;
        }
    }
}
