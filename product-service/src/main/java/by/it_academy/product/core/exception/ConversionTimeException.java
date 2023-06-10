package by.it_academy.product.core.exception;

import by.it_academy.product.core.dto.error.ErrorCode;
import lombok.Getter;

@Getter
public class ConversionTimeException extends RuntimeException {
    private ErrorCode errorCode;

    public ConversionTimeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

