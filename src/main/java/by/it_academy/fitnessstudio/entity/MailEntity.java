package by.it_academy.fitnessstudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mails", schema = "app")
public class MailEntity {
    @Id
    private String mail;

    private String text;
    public MailEntity() {
    }

    public MailEntity(String mail, String text) {
        this.mail = mail;
        this.text = text;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
