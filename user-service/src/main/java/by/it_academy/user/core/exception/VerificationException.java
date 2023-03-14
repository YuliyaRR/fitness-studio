package by.it_academy.user.core.exception;

import by.it_academy.user.core.dto.error.ErrorCode;

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
