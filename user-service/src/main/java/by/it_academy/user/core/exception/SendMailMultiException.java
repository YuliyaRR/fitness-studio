package by.it_academy.user.core.exception;

public class SendMailMultiException extends RuntimeException {

    public SendMailMultiException() {
    }

    public SendMailMultiException(String message) {
        super(message);
    }
}