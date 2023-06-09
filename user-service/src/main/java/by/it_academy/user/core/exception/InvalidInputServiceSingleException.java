package by.it_academy.user.core.exception;

import by.it_academy.user.core.dto.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidInputServiceSingleException extends IllegalArgumentException{
    private ErrorCode errorCode;

    public InvalidInputServiceSingleException(String s, ErrorCode errorCode) {
        super(s);
        this.errorCode = errorCode;
    }
}
