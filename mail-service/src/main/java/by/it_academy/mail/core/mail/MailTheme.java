package by.it_academy.mail.core.mail;

import java.util.HashMap;
import java.util.Map;

public enum MailTheme {
    VERIFICATION_CODE("Verification code");

    private final String description;

    private static final Map<String, MailTheme> ALL_THEME = new HashMap<>();

    static {
        for (MailTheme value : MailTheme.values()) {
            ALL_THEME.put(value.getDescription(), value);
        }
    }

    MailTheme(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static MailTheme getMailThemeByDescription(String description) {
        return ALL_THEME.get(description);
    }




}
