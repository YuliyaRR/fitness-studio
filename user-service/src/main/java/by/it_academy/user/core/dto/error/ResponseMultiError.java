package by.it_academy.user.core.dto.error;

import java.util.List;
import java.util.Objects;

public class ResponseMultiError {
    private ErrorCode logref;
    private List<LocalError> errors;

    public ResponseMultiError() {
    }

    public ResponseMultiError(ErrorCode logref, List<LocalError> errors) {
        this.logref = logref;
        this.errors = errors;
    }

    public ErrorCode getLogref() {
        return logref;
    }

    public void setLogref(ErrorCode logref) {
        this.logref = logref;
    }

    public List<LocalError> getErrors() {
        return errors;
    }

    public void setErrors(List<LocalError> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMultiError that = (ResponseMultiError) o;
        return Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors);
    }
}
