package by.it_academy.audit.entity;

import by.it_academy.audit.core.dto.EssenceType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "audit")
public class AuditEntity {
    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "user_uuid")
    private UUID userUUID;
    @Column(name = "user_mail")
    private String userMail;
    @Column(name = "user_fio")
    private String userFio;
    @Column(name = "user_role")
    private String role;
    private String text;
    @Enumerated(EnumType.STRING)
    private EssenceType type;
    private String id;

    public AuditEntity() {
    }

    public AuditEntity(UUID uuid, LocalDateTime dtCreate,
                       UUID userUUID, String userMail,
                       String userFio, String role,
                       String text, EssenceType type, String id) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.userUUID = userUUID;
        this.userMail = userMail;
        this.userFio = userFio;
        this.role = role;
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

    public UUID getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserFio() {
        return userFio;
    }

    public void setUserFio(String userFio) {
        this.userFio = userFio;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        AuditEntity that = (AuditEntity) o;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(dtCreate, that.dtCreate)
                && Objects.equals(userUUID, that.userUUID)
                && Objects.equals(userMail, that.userMail)
                && Objects.equals(userFio, that.userFio)
                &&Objects.equals(role, that.role)
                && Objects.equals(text, that.text)
                && type == that.type && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, userUUID, userMail, userFio, role, text, type, id);
    }
}
