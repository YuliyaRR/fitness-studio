package by.it_academy.user.core.dto.user;

import by.it_academy.user.validator.api.ValidEmail;
import by.it_academy.user.validator.api.ValidPassword;
import by.it_academy.user.validator.api.ValidString;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserLogin {
    @ValidString
    @ValidEmail
    private String mail;
    @ValidString
    @ValidPassword
    private String password;
}
