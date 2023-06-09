package by.it_academy.user.core.exception;

import by.it_academy.user.core.dto.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidLoginException extends RuntimeException {
    private ErrorCode errorCode;

    public InvalidLoginException(String s, ErrorCode errorCode) {
        super(s);
        this.errorCode = errorCode;
    }
}
