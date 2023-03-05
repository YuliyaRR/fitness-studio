package by.it_academy.fitnessstudio.service.api;

import by.it_academy.fitnessstudio.core.dto.VerificationCode;

public interface IVerificationService {

    void save(VerificationCode verificationCode);

    void verify(VerificationCode  verificationCode);


}
