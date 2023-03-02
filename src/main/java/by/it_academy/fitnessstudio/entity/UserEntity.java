package by.it_academy.fitnessstudio.entity;

import by.it_academy.fitnessstudio.core.dto.user.UserRole;
import by.it_academy.fitnessstudio.core.dto.user.UserStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "users")
@SecondaryTable(schema = "app", name = "verification",
    pkJoinColumns = {
        @PrimaryKeyJoinColumn(name = "uuid")
    }
)
public class UserEntity {
    @Id
    private UUID uuid;
    private String mail;
    private String fio;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String password;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    @Version
    private LocalDateTime dtUpdate;
    @Column(name = "code", table = "verification")
    private Integer code;

    public UserEntity() {
    }

    public UserEntity(String mail, String fio, String password) {//client
        this.mail = mail;
        this.fio = fio;
        this.password = password;

    }

    public UserEntity(String mail, String fio, UserRole role, String password) {//stuff
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.password = password;
    }

    public UserEntity(UUID uuid, String mail,
                      String fio, UserRole role,
                      UserStatus status, String password,
                      LocalDateTime dtCreate, LocalDateTime dtUpdate,
                      Integer code) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.code = code;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static class UserEntityBuilder {
        private UUID uuid;
        private String mail;
        private String fio;
        private UserRole role;
        private UserStatus status;
        private String password;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private Integer code;

        private UserEntityBuilder() {
        }

        public static UserEntityBuilder create() {
            return new UserEntityBuilder();
        }

        public UserEntityBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public UserEntityBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public UserEntityBuilder setFio(String fio) {
            this.fio = fio;
            return this;
        }

        public UserEntityBuilder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public UserEntityBuilder setStatus(UserStatus status) {
            this.status = status;
            return this;
        }

        public UserEntityBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserEntityBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public UserEntityBuilder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public UserEntityBuilder setCode(Integer code) {
            this.code = code;
            return this;
        }

        public UserEntity build() {
            return  new UserEntity(uuid, mail, fio, role, status, password, dtCreate, dtUpdate, code);
        }
    }
}



