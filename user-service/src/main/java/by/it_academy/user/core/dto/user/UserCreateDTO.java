package by.it_academy.user.core.dto.user;

import by.it_academy.user.validator.api.ValidEmail;
import by.it_academy.user.validator.api.ValidPassword;
import by.it_academy.user.validator.api.ValidString;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class UserCreateDTO {
    @ValidString
    @ValidEmail
    private String mail;
    @ValidString
    private String fio;
    @NotNull(message = "The entered value doesn't exist")
    private UserRole role;
    @NotNull(message = "The entered value doesn't exist")
    private UserStatus status;
    @ValidString
    @ValidPassword
    private String password;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String mail, String fio, UserRole role, UserStatus status, String password) {
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
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
        UserCreateDTO userCreateDTO = (UserCreateDTO) o;
        return Objects.equals(mail, userCreateDTO.mail)
                && Objects.equals(fio, userCreateDTO.fio)
                && role == userCreateDTO.role
                && status == userCreateDTO.status
                && Objects.equals(password, userCreateDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, fio, role, status, password);
    }

    public static class UserCreateBuilder {
        private String mail;

        private String fio;

        private UserRole role;

        private UserStatus status;

        private String password;

        private UserCreateBuilder() {
        }

        public static UserCreateBuilder create(){
            return new UserCreateBuilder();
        }


        public UserCreateBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public UserCreateBuilder setFio(String fio) {
            this.fio = fio;
            return this;
        }

        public UserCreateBuilder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public UserCreateBuilder setStatus(UserStatus status) {
            this.status = status;
            return this;
        }

        public UserCreateBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserCreateDTO build() {
            return new UserCreateDTO(mail, fio, role, status, password);
        }
    }
}
