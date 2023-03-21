package by.it_academy.product.core.exception;

public class SendMultiException extends RuntimeException {

    public SendMultiException() {
    }

    public SendMultiException(String message) {
        super(message);
    }
}