package by.it_academy.audit.core.dto;

import by.it_academy.audit.converters.LocalDateTimeToLongMillisSerializer;
import by.it_academy.audit.converters.StringToLDTJsonDeserializer;
import by.it_academy.audit.validator.api.ValidString;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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
}
