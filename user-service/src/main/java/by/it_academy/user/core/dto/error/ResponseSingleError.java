package by.it_academy.user.core.dto.error;

import java.util.Objects;

public class ResponseSingleError {
    private ErrorCode logref;
    private String message;

    public ResponseSingleError() {
    }

    public ResponseSingleError(ErrorCode logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public ErrorCode getLogref() {
        return logref;
    }

    public void setLogref(ErrorCode logref) {
        this.logref = logref;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseSingleError that = (ResponseSingleError) o;
        return Objects.equals(logref, that.logref) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logref, message);
    }
}
