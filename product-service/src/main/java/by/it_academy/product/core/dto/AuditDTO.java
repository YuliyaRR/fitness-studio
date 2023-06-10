package by.it_academy.product.core.dto;

import by.it_academy.product.audit.EssenceType;
import by.it_academy.product.core.dto.user.UserToken;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AuditDTO {
    private UUID uuid;
    @SerializedName("dt_create")
    private LocalDateTime dtCreate;
    private UserToken user;
    private String text;
    private EssenceType type;
    private String id;
}
