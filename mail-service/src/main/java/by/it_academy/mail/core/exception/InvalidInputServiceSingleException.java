package by.it_academy.mail.core.exception;

import by.it_academy.mail.core.error.ErrorCode;

public class InvalidInputServiceSingleException extends IllegalArgumentException{
    private ErrorCode errorCode;

    public InvalidInputServiceSingleException(String s) {
        super(s);
    }

    public InvalidInputServiceSingleException(String s, ErrorCode errorCode) {
        super(s);
        this.errorCode = errorCode;
    }

    public InvalidInputServiceSingleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputServiceSingleException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
