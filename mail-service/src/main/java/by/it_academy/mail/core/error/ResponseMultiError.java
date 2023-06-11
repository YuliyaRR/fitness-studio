package by.it_academy.mail.core.error;

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
