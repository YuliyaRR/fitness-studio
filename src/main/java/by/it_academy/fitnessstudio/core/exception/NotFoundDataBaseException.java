package by.it_academy.fitnessstudio.core.exception;

public class NotFoundDataBaseException extends RuntimeException {

    private String field;
    public NotFoundDataBaseException(String message) {
        super(message);
    }

    public NotFoundDataBaseException() {
    }

    public NotFoundDataBaseException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

