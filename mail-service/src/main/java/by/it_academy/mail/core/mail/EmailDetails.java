package by.it_academy.mail.core.mail;

import by.it_academy.mail.validator.api.ValidEmail;
import by.it_academy.mail.validator.api.ValidString;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    @ValidString
    private String subject;
    @ValidString
    @ValidEmail
    private String recipient;
    @ValidString
    private String msgBody;
}
