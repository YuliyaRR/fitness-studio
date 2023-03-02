package by.it_academy.fitnessstudio.core.exception;

public class InvalidInputServiceMultiException extends IllegalArgumentException{

    private String field;

    public InvalidInputServiceMultiException(String s) {
        super(s);
    }

    public InvalidInputServiceMultiException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputServiceMultiException(Throwable cause) {
        super(cause);
    }

    public InvalidInputServiceMultiException(String s, String field) {
        super(s);
        this.field = field;
    }

    @Override
    public String getLocalizedMessage() {
        return field;
    }
}
