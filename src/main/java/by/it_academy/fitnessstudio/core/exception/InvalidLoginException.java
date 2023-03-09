package by.it_academy.fitnessstudio.core.exception;

import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;

public class InvalidLoginException extends RuntimeException{
    private ErrorCode errorCode;
    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(String s, ErrorCode errorCode) {
        super(s);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
