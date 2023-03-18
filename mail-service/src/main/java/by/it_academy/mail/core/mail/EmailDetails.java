package by.it_academy.mail.core.mail;

import by.it_academy.mail.validator.api.ValidEmail;
import by.it_academy.mail.validator.api.ValidString;

import java.util.Objects;

public class EmailDetails {
    @ValidString
    private String subject;
    @ValidString
    @ValidEmail
    private String recipient;
    @ValidString
    private String msgBody;

    public EmailDetails() {
    }

    public EmailDetails(String subject, String recipient, String msgBody) {
        this.subject = subject;
        this.recipient = recipient;
        this.msgBody = msgBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailDetails that = (EmailDetails) o;
        return Objects.equals(subject, that.subject) && Objects.equals(recipient, that.recipient) && Objects.equals(msgBody, that.msgBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, recipient, msgBody);
    }
}
