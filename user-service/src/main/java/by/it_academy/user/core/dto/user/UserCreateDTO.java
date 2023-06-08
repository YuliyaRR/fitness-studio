package by.it_academy.user.core.dto.user;

import by.it_academy.user.validator.api.ValidEmail;
import by.it_academy.user.validator.api.ValidPassword;
import by.it_academy.user.validator.api.ValidString;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder(setterPrefix = "set")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    @ValidString
    @ValidEmail
    private String mail;
    @ValidString
    private String fio;
    @NotNull(message = "The entered value doesn't exist")
    private UserRole role;
    @NotNull(message = "The entered value doesn't exist")
    private UserStatus status;
    @ValidString
    @ValidPassword
    private String password;
}
