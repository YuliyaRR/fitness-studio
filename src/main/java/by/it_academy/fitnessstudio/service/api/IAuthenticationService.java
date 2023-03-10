package by.it_academy.fitnessstudio.service.api;

import by.it_academy.fitnessstudio.core.dto.VerificationCode;
import by.it_academy.fitnessstudio.core.dto.user.User;
import by.it_academy.fitnessstudio.core.dto.user.UserLogin;
import by.it_academy.fitnessstudio.core.dto.user.UserRegistration;
import by.it_academy.fitnessstudio.validator.api.ValidEmail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IAuthenticationService {

    void registration(@NotNull @Valid UserRegistration userRegistration);

    void verification(@NotNull @Valid VerificationCode verificationCode);

    String logIn(@NotNull @Valid UserLogin userLogin);

    User get(@ValidEmail String mail);
}
