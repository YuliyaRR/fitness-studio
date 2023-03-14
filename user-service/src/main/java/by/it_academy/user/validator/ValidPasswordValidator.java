package by.it_academy.user.validator;

import by.it_academy.user.validator.api.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value != null && value.length() < 8) {
            return false;
        }

        return true;

    }
}
