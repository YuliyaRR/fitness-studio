package by.it_academy.user.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditAction {
    CREATE("Created new user in the system"),
    UPDATED("Updated user in the system");

    private final String description;
}
