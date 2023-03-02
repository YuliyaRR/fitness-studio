package by.it_academy.fitnessstudio.entity;

import by.it_academy.fitnessstudio.core.dto.user.UserRole;
import by.it_academy.fitnessstudio.core.dto.user.UserStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "staff", schema = "app")
public class StaffEntity {
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

    public StaffEntity() {
    }

    public StaffEntity(String mail, String fio, UserRole role, String password) {//TODO: билдер
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
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


    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffEntity that = (StaffEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(mail, that.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, mail);
    }
}
