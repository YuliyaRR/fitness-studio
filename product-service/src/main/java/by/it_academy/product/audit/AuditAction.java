package by.it_academy.product.audit;

public enum AuditAction {
    CREATE_PRODUCT("Created new product in the system"),
    CREATE_RECIPE("Created new recipe in the system"),
    UPDATED_PRODUCT("Updated product in the system"),
    UPDATED_RECIPE("Updated recipe in the system");

    private final String description;

    AuditAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
