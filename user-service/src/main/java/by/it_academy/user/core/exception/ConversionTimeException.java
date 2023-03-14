package by.it_academy.user.core.exception;

import by.it_academy.user.core.dto.error.ErrorCode;

public class ConversionTimeException extends RuntimeException {
    private ErrorCode errorCode;

    public ConversionTimeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ConversionTimeException() {
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}

