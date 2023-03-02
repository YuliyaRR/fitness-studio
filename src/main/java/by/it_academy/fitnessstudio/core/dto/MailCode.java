package by.it_academy.fitnessstudio.core.dto;

import java.util.Objects;

public class MailCode {
    private String mail;
    private String code;

    public MailCode(String mail, String code) {
        this.mail = mail;
        this.code = code;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailCode mailCode = (MailCode) o;
        return Objects.equals(mail, mailCode.mail) && Objects.equals(code, mailCode.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, code);
    }
}
