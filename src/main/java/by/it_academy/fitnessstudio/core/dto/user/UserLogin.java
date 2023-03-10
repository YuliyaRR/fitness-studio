package by.it_academy.fitnessstudio.core.dto.user;

import by.it_academy.fitnessstudio.validator.api.ValidEmail;
import by.it_academy.fitnessstudio.validator.api.ValidPassword;
import by.it_academy.fitnessstudio.validator.api.ValidString;

import java.util.Objects;

public class UserLogin {
    @ValidString
    @ValidEmail
    private String mail;
    @ValidString
    @ValidPassword
    private String password;

    public UserLogin() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
        UserLogin userLogin = (UserLogin) o;
        return Objects.equals(mail, userLogin.mail) && Objects.equals(password, userLogin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, password);
    }
}
