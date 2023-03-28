package by.it_academy.mail.entity;

import by.it_academy.mail.core.mail.MailTheme;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "mails", schema = "app")
public class MailEntity {
    @Id
    private UUID uuid;
    private String message;
    private String email;
    @Enumerated(EnumType.STRING)
    private MailTheme theme;
    @Column(name = "is_send")
    private boolean isSend;
    @Column(name = "attempt_of_sending")
    private int attemptOfSending;
    @Column(name = "limit_of_attempts")
    private int limitOfAttempts;

    public MailEntity() {
    }

    public MailEntity(UUID uuid, String message,
                      String email, MailTheme theme, boolean isSend,
                      int attemptOfSending, int limitOfAttempts) {
        this.uuid = uuid;
        this.message = message;
        this.email = email;
        this.theme = theme;
        this.isSend = isSend;
        this.attemptOfSending = attemptOfSending;
        this.limitOfAttempts = limitOfAttempts;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public int getAttemptOfSending() {
        return attemptOfSending;
    }

    public void setAttemptOfSending(int attemptOfSending) {
        this.attemptOfSending = attemptOfSending;
    }

    public int getLimitOfAttempts() {
        return limitOfAttempts;
    }

    public void setLimitOfAttempts(int limitOfAttempts) {
        this.limitOfAttempts = limitOfAttempts;
    }

    public MailTheme getTheme() {
        return theme;
    }

    public void setTheme(MailTheme theme) {
        this.theme = theme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailEntity that = (MailEntity) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
