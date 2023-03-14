package by.it_academy.user.service.api;

import by.it_academy.user.core.dto.VerificationCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IVerificationService {

    void save(@NotNull @Valid VerificationCode verificationCode);

    void verify(@NotNull @Valid VerificationCode  verificationCode);


}
