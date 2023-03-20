package by.it_academy.audit.converters;

import by.it_academy.audit.core.dto.AuditDTO;
import by.it_academy.audit.entity.AuditEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuditDtoToEntityConverter implements Converter<AuditDTO, AuditEntity> {
    @Override
    public AuditEntity convert(AuditDTO source) {
        AuditEntity entity = new AuditEntity();
        entity.setUuid(source.getUuid());
        entity.setDtCreate(source.getDtCreate());
        entity.setUserUUID(source.getUser().getUuid());
        entity.setUserMail(source.getUser().getMail());
        entity.setUserFio(source.getUser().getFio());
        entity.setRole(source.getUser().getRole());
        entity.setText(source.getText());
        entity.setType(source.getType());
        entity.setId(source.getId());

        return entity;
    }
}
