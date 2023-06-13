package by.it_academy.audit.core.exception;

import by.it_academy.audit.core.dto.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidInputServiceSingleException extends IllegalArgumentException{
    private ErrorCode errorCode;

    public InvalidInputServiceSingleException(String s, ErrorCode errorCode) {
        super(s);
        this.errorCode = errorCode;
    }
}
