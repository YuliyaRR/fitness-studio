package by.it_academy.user.core.dto.user;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserAudit {
    private UUID uuid;
    private String mail;
    private String fio;
    private UserRole role;
}
