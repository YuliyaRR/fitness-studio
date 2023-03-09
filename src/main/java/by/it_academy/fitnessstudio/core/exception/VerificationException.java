package by.it_academy.fitnessstudio.core.exception;

import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;

public class VerificationException extends RuntimeException{
    private ErrorCode errorCode;

    public VerificationException(String s, ErrorCode errorCode) {
        super(s);
        this.errorCode = errorCode;
    }

    public VerificationException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
