package by.it_academy.fitnessstudio.core.dto.error;

import java.util.List;
import java.util.Objects;

public class ResponseMultiError {
    private final String logref = "structured_error";
    private List<LocalError> errors;

    public ResponseMultiError() {
    }

    /*public ResponseMultiError(String logref, List<LocalError> errors) {
        this.logref = logref;
        this.errors = errors;
    }*/

    public ResponseMultiError(List<LocalError> errors) {
        this.errors = errors;
    }

    public String getLogref() {
        return logref;
    }

    /*public void setLogref(String logref) {
        this.logref = logref;
    }*/

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
