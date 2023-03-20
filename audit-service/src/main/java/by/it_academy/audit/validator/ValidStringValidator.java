package by.it_academy.audit.validator;

import by.it_academy.audit.validator.api.ValidString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidStringValidator implements ConstraintValidator<ValidString, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isBlank() || value.isEmpty()) {
            return false;
        }
        return true;
    }
}
