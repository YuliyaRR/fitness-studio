package by.it_academy.audit.core.dto;

import by.it_academy.audit.converters.LocalDateTimeToLongMillisSerializer;
import by.it_academy.audit.converters.StringToLDTJsonDeserializer;
import by.it_academy.audit.validator.api.ValidString;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@JsonPropertyOrder({"uuid", "dt_create"})
public class AuditDTO {
    @NotNull
    private UUID uuid;
    @NotNull
    @JsonProperty(value = "dt_create")
    @JsonSerialize(using = LocalDateTimeToLongMillisSerializer.class)
    @JsonDeserialize(using = StringToLDTJsonDeserializer.class)
    private LocalDateTime dtCreate;
    @NotNull
    private UserToken user;
    @ValidString
    private String text;
    @NotNull
    private EssenceType type;
    @ValidString
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
