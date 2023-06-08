package by.it_academy.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "app", name = "users")
public class UserEntity {
    @Id
    private UUID uuid;
    private String mail;
    private String fio;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role")
    private RoleEntity role;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status")
    private StatusEntity status;
    private String password;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    @Version
    private LocalDateTime dtUpdate;
}



