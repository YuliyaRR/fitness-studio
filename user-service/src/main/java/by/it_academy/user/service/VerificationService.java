package by.it_academy.user.service;

import by.it_academy.user.core.dto.VerificationCode;
import by.it_academy.user.core.dto.error.ErrorCode;
import by.it_academy.user.core.exception.VerificationException;
import by.it_academy.user.entity.VerificationCodeEntity;
import by.it_academy.user.repositories.api.VerificationCodeEntityRepository;
import by.it_academy.user.service.api.IVerificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class VerificationService implements IVerificationService {
    private final VerificationCodeEntityRepository repository;

    public VerificationService(VerificationCodeEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(@NotNull @Valid VerificationCode verificationCode) {
        repository.save(new VerificationCodeEntity(verificationCode.getCode(), verificationCode.getMail()));
    }

    @Override
    public void verify(@NotNull @Valid VerificationCode verificationCode) {

        VerificationCodeEntity verificationCodeEntity = repository.findById(verificationCode.getCode())
                .orElseThrow(() -> new VerificationException("Invalid verification code", ErrorCode.ERROR));

        if (verificationCode.getMail().equals(verificationCodeEntity.getEmail())) {
            repository.delete(verificationCodeEntity);
        } else {
            throw new VerificationException("Invalid email", ErrorCode.ERROR);
        }
    }
}
