package by.it_academy.audit.core.dto.error;

import lombok.*;

import java.util.List;
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMultiError {
    private ErrorCode logref;
    private List<LocalError> errors;
}
