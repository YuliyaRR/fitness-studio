package by.it_academy.product.core.exception;

public class SendMailMultiException extends RuntimeException {

    public SendMailMultiException() {
    }

    public SendMailMultiException(String message) {
        super(message);
    }
}