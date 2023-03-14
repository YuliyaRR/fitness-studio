package by.it_academy.product.validator.api;

public interface IValidator<T> {

    void validate(T item);
}
