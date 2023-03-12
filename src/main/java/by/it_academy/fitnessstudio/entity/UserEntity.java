package by.it_academy.fitnessstudio.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "users")
public class UserEntity {
    @Id
    private UUID uuid;
    private String mail;
    private String fio;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role")
    private RoleEntity role;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status")
    private StatusEntity status;
    private String password;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    @Version
    private LocalDateTime dtUpdate;

    public UserEntity() {
    }

    public UserEntity(UUID uuid, String mail,
                      String fio, RoleEntity role,
                      StatusEntity status, String password,
                      LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
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

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
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


    public static class UserEntityBuilder {
        private UUID uuid;
        private String mail;
        private String fio;
        private RoleEntity role;
        private StatusEntity status;
        private String password;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;

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

        public UserEntityBuilder setRole(RoleEntity role) {
            this.role = role;
            return this;
        }

        public UserEntityBuilder setStatus(StatusEntity status) {
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

        public UserEntity build() {
            return  new UserEntity(uuid, mail, fio, role, status, password, dtCreate, dtUpdate);
        }
    }
}



