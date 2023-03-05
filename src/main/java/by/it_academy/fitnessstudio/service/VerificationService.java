package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.VerificationCode;
import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceSingleException;
import by.it_academy.fitnessstudio.core.exception.VerificationException;
import by.it_academy.fitnessstudio.entity.VerificationCodeEntity;
import by.it_academy.fitnessstudio.repositories.api.VerificationCodeEntityRepository;
import by.it_academy.fitnessstudio.service.api.IVerificationService;

import java.util.Optional;
import java.util.UUID;

public class VerificationService implements IVerificationService {
    private final VerificationCodeEntityRepository repository;

    public VerificationService(VerificationCodeEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(VerificationCode verificationCode) {
        if (verificationCode == null) {
            throw new InvalidInputServiceSingleException("Verification code not entered", ErrorCode.ERROR);
        }

        repository.save(new VerificationCodeEntity(verificationCode.getCode(), verificationCode.getMail()));
    }

    @Override
    public void verify(VerificationCode verificationCode) {
        if (verificationCode == null) {
            throw new InvalidInputServiceSingleException("Verification code not entered", ErrorCode.ERROR);
        }

        validateVerification(verificationCode);

        Optional<VerificationCodeEntity> byCode = repository.findById(verificationCode.getCode());

        if (byCode.isEmpty()) {
            throw new InvalidInputServiceSingleException("This code was not found in the database", ErrorCode.ERROR);
        }

        VerificationCodeEntity verificationCodeEntity = byCode.get();

        if (verificationCode.getCode().equals(verificationCodeEntity.getCode())) {
            repository.delete(verificationCodeEntity);
        } else {
            throw new VerificationException("Invalid verification code", ErrorCode.ERROR);
        }

    }

    private void validateVerification(VerificationCode verificationCode) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);
        UUID code = verificationCode.getCode();
        String mail = verificationCode.getMail();

        if(code == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Code not entered", "code"));
        }

        if(mail == null || mail.isBlank() || mail.isEmpty()){
            multiException.addSuppressed(new InvalidInputServiceMultiException("Email not entered", "mail"));
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }
    }
}
