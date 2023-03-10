package by.it_academy.fitnessstudio.core.dto.user;

import by.it_academy.fitnessstudio.validator.api.ValidEmail;
import by.it_academy.fitnessstudio.validator.api.ValidPassword;
import by.it_academy.fitnessstudio.validator.api.ValidString;

import java.util.Objects;

public class UserRegistration {
    @ValidString
    @ValidEmail
    private String mail;
    @ValidString
    private String fio;
    @ValidString
    @ValidPassword
    private String password;

    public UserRegistration() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistration that = (UserRegistration) o;
        return Objects.equals(mail, that.mail)
                && Objects.equals(fio, that.fio)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, fio, password);
    }
}
