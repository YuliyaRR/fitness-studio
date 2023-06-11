package by.it_academy.mail.entity;

import by.it_academy.mail.core.mail.MailTheme;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "mails", schema = "app")
public class MailEntity {
    @Id
    @EqualsAndHashCode.Include
    private UUID uuid;
    @EqualsAndHashCode.Include
    private String message;
    private String email;
    @Enumerated(EnumType.STRING)
    private MailTheme theme;
    @Column(name = "is_send")
    private boolean isSend;
    @Column(name = "attempt_of_sending")
    private int attemptOfSending;
    @Column(name = "limit_of_attempts")
    private int limitOfAttempts;
}
