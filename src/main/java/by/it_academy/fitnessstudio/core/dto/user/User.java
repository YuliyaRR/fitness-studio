package by.it_academy.fitnessstudio.core.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

public class User {
    @JsonProperty(index = 1)
    private UUID uuid;
    @JsonProperty(value = "dt_create", index = 2)
    private Long dtCreate;
    @JsonProperty(value = "dt_update", index = 3)
    private Long dtUpdate;
    private String mail;
    private String fio;
    private UserRole role;
    private UserStatus status;

    public User() {
    }

    public User(UUID uuid, Long dtCreate,
                Long dtUpdate, String mail,
                String fio, UserRole role, UserStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Long dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Long getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Long dtUpdate) {
        this.dtUpdate = dtUpdate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uuid, user.uuid) && Objects.equals(mail, user.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, mail);
    }

    public static class UserBuilder {
        private UUID uuid;
        private Long dtCreate;
        private Long dtUpdate;
        private String mail;
        private String fio;
        private UserRole role;
        private UserStatus status;

        private UserBuilder() {
        }

        public static UserBuilder create(){
            return new UserBuilder();
        }

        public UserBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public UserBuilder setDtCreate(Long dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public UserBuilder setDtUpdate(Long dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public UserBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public UserBuilder setFio(String fio) {
            this.fio = fio;
            return this;
        }

        public UserBuilder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public UserBuilder setStatus(UserStatus status) {
            this.status = status;
            return this;
        }

        public User build() {
            return new User(uuid, dtCreate, dtUpdate, mail, fio, role, status);
        }
    }


}
