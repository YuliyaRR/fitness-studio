package by.it_academy.user.core.dto.user;

import java.util.Objects;
import java.util.UUID;

public class UserAudit {
    private UUID uuid;
    private String mail;
    private String fio;
    private UserRole role;

    public UserAudit() {
    }

    public UserAudit(UUID uuid, String mail, String fio, UserRole role) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAudit userAudit = (UserAudit) o;
        return Objects.equals(uuid, userAudit.uuid) && Objects.equals(mail, userAudit.mail) && Objects.equals(fio, userAudit.fio) && role == userAudit.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, mail, fio, role);
    }
}
