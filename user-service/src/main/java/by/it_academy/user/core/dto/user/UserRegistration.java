package by.it_academy.user.core.dto.user;

import by.it_academy.user.validator.api.ValidEmail;
import by.it_academy.user.validator.api.ValidPassword;
import by.it_academy.user.validator.api.ValidString;
import lombok.*;
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserRegistration {
    @ValidString
    @ValidEmail
    private String mail;
    @ValidString
    private String fio;
    @ValidString
    @ValidPassword
    private String password;
}
