package by.it_academy.user.core.dto;

import by.it_academy.user.audit.EssenceType;
import by.it_academy.user.core.dto.user.UserToken;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class AuditDTO {
    private UUID uuid;
    @SerializedName("dt_create")
    private LocalDateTime dtCreate;
    private UserToken user;
    private String text;
    private EssenceType type;
    private String id;

    public AuditDTO() {
    }

    public AuditDTO(UUID uuid, LocalDateTime dtCreate,
                    UserToken user, String text,
                    EssenceType type, String id) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public UserToken getUser() {
        return user;
    }

    public void setUser(UserToken user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EssenceType getType() {
        return type;
    }

    public void setType(EssenceType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditDTO auditDTO = (AuditDTO) o;
        return Objects.equals(uuid, auditDTO.uuid)
                && Objects.equals(dtCreate, auditDTO.dtCreate)
                && Objects.equals(user, auditDTO.user)
                && Objects.equals(text, auditDTO.text)
                && type == auditDTO.type
                && Objects.equals(id, auditDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, user, text, type, id);
    }
}
