package by.it_academy.user.core.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailTheme {
    VERIFICATION_CODE("Verification code");

    private final String description;

    /*MailTheme(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }*/
}
