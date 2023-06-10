package by.it_academy.product.core.dto.error;

import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSingleError {
    private ErrorCode logref;
    private String message;
}
