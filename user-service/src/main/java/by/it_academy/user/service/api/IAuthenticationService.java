package by.it_academy.user.service.api;

import by.it_academy.user.core.dto.VerificationCode;
import by.it_academy.user.core.dto.user.User;
import by.it_academy.user.core.dto.user.UserLogin;
import by.it_academy.user.core.dto.user.UserRegistration;
import by.it_academy.user.core.dto.user.UserToken;
import by.it_academy.user.validator.api.ValidEmail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IAuthenticationService {

    void registration(@NotNull @Valid UserRegistration userRegistration);

    void verification(@NotNull @Valid VerificationCode verificationCode);

    UserToken logIn(@NotNull @Valid UserLogin userLogin);

    User get(@ValidEmail String mail);
}
