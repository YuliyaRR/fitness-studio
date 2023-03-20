package by.it_academy.user.audit;

public enum AuditAction {
    CREATE("Created new user in the system"),
    UPDATED("Updated user in the system");

    private final String description;

    AuditAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
