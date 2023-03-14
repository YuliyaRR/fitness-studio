package by.it_academy.product.core.exception;

import by.it_academy.product.core.dto.error.ErrorCode;

public class InvalidInputServiceMultiException extends IllegalArgumentException{

    private String field;

    private ErrorCode errorCode;

    public InvalidInputServiceMultiException(String s) {
        super(s);
    }

    public InvalidInputServiceMultiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public InvalidInputServiceMultiException(String s, String field) {
        super(s);
        this.field = field;
    }

    @Override
    public String getLocalizedMessage() {
        return field;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
