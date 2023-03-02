package by.it_academy.fitnessstudio.core.exception;

public class InvalidInputServiceSingleException extends IllegalArgumentException{

    public InvalidInputServiceSingleException(String s) {
        super(s);
    }

    public InvalidInputServiceSingleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputServiceSingleException(Throwable cause) {
        super(cause);
    }

}
