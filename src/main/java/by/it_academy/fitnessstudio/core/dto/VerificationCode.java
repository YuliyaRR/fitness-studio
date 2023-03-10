package by.it_academy.fitnessstudio.core.dto;

import by.it_academy.fitnessstudio.validator.api.ValidEmail;
import by.it_academy.fitnessstudio.validator.api.ValidString;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

public class VerificationCode {
    @ValidString
    @ValidEmail
    private String mail;
    @NotNull
    private UUID code;

    public VerificationCode(String mail, UUID code) {
        this.mail = mail;
        this.code = code;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UUID getCode() {
        return code;
    }

    public void setCode(UUID code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationCode verificationCode = (VerificationCode) o;
        return Objects.equals(mail, verificationCode.mail) && Objects.equals(code, verificationCode.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, code);
    }
}
