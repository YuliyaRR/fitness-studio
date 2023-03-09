package by.it_academy.fitnessstudio.validator.api;

import by.it_academy.fitnessstudio.validator.ValidPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= ValidPasswordValidator.class)
public @interface ValidPassword {
    String message() default "Password can't be less than 8 characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
