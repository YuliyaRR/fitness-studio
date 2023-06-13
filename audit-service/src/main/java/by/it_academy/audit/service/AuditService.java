package by.it_academy.audit.service;

import by.it_academy.audit.core.dto.AuditDTO;
import by.it_academy.audit.core.dto.error.ErrorCode;
import by.it_academy.audit.core.dto.OnePage;
import by.it_academy.audit.core.exception.ConversionTimeException;
import by.it_academy.audit.core.exception.InvalidInputServiceSingleException;
import by.it_academy.audit.entity.AuditEntity;
import by.it_academy.audit.repositories.api.AuditEntityRepository;
import by.it_academy.audit.service.api.IAuditService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;
@Service
@Validated
@RequiredArgsConstructor
public class AuditService implements IAuditService {
    private final AuditEntityRepository repository;
    private final ConversionService conversionService;

    @Override
    public void save(@NotNull @Valid AuditDTO auditDTO) {
        if(!conversionService.canConvert(AuditDTO.class, AuditEntity.class)) {
            throw new ConversionTimeException("Unable to convert");
        }
        AuditEntity auditEntity = conversionService.convert(auditDTO, AuditEntity.class);
        repository.save(auditEntity);
    }

    @Override
    public OnePage<AuditDTO> getPage(@NotNull Pageable pageable) {
        Page<AuditEntity> all = repository.findAll(pageable);

        if(!conversionService.canConvert(AuditEntity.class, AuditDTO.class)) {
            throw new ConversionTimeException("Unable to convert");
        }

        List<AuditDTO> content = all.get().map(entity -> conversionService.convert(entity, AuditDTO.class)).toList();

        return OnePage.OnePageBuilder.create(content)
                .setNumber(all.getNumber())
                .setSize(all.getSize())
                .setTotalPages(all.getTotalPages())
                .setTotalElements(all.getTotalElements())
                .setFirst(all.isFirst())
                .setNumberOfElements(all.getNumberOfElements())
                .setLast(all.isLast())
                .build();
    }

    @Override
    public AuditDTO get(@NotNull UUID uuid) {
        AuditEntity entity = repository.findById(uuid)
                .orElseThrow(() -> new InvalidInputServiceSingleException("Record with this uuid was not found in the database", ErrorCode.ERROR));
        if(!conversionService.canConvert(AuditEntity.class, AuditDTO.class)) {
            throw new ConversionTimeException("Unable to convert");
        }
        return conversionService.convert(entity, AuditDTO.class);
    }
}
