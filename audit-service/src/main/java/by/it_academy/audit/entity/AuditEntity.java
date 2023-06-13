package by.it_academy.audit.entity;

import by.it_academy.audit.core.dto.EssenceType;
import by.it_academy.audit.core.dto.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "app", name = "audit")
public class AuditEntity {
    @Id
    @EqualsAndHashCode.Include
    private UUID uuid;
    @EqualsAndHashCode.Include
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @EqualsAndHashCode.Include
    @Column(name = "user_uuid")
    private UUID userUUID;
    @Column(name = "user_mail")
    private String userMail;
    @Column(name = "user_fio")
    private String userFio;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String text;
    @Enumerated(EnumType.STRING)
    private EssenceType type;
    @EqualsAndHashCode.Include
    private String id;
}
