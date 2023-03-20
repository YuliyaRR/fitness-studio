package by.it_academy.audit.service.api;

import by.it_academy.audit.core.dto.AuditDTO;
import by.it_academy.audit.core.dto.OnePage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAuditService {
    void save (@NotNull @Valid AuditDTO auditDTO);
    OnePage<AuditDTO>getPage(@NotNull Pageable pageable);
    AuditDTO get(@NotNull UUID uuid);

}
