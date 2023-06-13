package by.it_academy.audit.converters;

import by.it_academy.audit.core.dto.AuditDTO;
import by.it_academy.audit.entity.AuditEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuditDtoToEntityConverter implements Converter<AuditDTO, AuditEntity> {
    @Override
    public AuditEntity convert(AuditDTO source) {
        return AuditEntity.builder()
                .uuid(source.getUuid())
                .dtCreate(source.getDtCreate())
                .userUUID(source.getUser().getUuid())
                .userMail(source.getUser().getMail())
                .userFio(source.getUser().getFio())
                .role(source.getUser().getRole())
                .text(source.getText())
                .type(source.getType())
                .id(source.getId())
                .build();
    }
}
