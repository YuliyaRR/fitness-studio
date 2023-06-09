package by.it_academy.user.core.dto;

import by.it_academy.user.validator.api.ValidEmail;
import by.it_academy.user.validator.api.ValidString;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class VerificationCode {
    @ValidString
    @ValidEmail
    private String mail;
    @NotNull
    private UUID code;
}
