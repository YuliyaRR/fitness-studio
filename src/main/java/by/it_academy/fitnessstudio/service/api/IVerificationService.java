package by.it_academy.fitnessstudio.service.api;

import by.it_academy.fitnessstudio.core.dto.VerificationCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IVerificationService {

    void save(@NotNull @Valid VerificationCode verificationCode);

    void verify(@NotNull @Valid VerificationCode  verificationCode);


}
