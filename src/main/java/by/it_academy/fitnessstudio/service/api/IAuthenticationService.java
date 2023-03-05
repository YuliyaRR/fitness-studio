package by.it_academy.fitnessstudio.service.api;

import by.it_academy.fitnessstudio.core.dto.VerificationCode;
import by.it_academy.fitnessstudio.core.dto.user.User;
import by.it_academy.fitnessstudio.core.dto.user.UserLogin;
import by.it_academy.fitnessstudio.core.dto.user.UserRegistration;

import java.util.UUID;

public interface IAuthenticationService {

    void registration(UserRegistration userRegistration);

    void verification(VerificationCode verificationCode);

    void logIn(UserLogin userLogin);

    User get(UUID uuid);
}
