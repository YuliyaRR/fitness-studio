package by.it_academy.audit.converters;

import by.it_academy.audit.core.dto.AuditDTO;
import by.it_academy.audit.core.dto.UserToken;
import by.it_academy.audit.entity.AuditEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuditEntityToAuditDtoConverter implements Converter<AuditEntity, AuditDTO> {
    @Override
    public AuditDTO convert(AuditEntity source) {
        AuditDTO auditDTO = new AuditDTO();

        auditDTO.setUuid(source.getUuid());
        auditDTO.setDtCreate(source.getDtCreate());
        auditDTO.setText(source.getText());
        auditDTO.setType(source.getType());
        auditDTO.setId(source.getId());

        UserToken user = new UserToken();
        user.setUuid(source.getUserUUID());
        user.setMail(source.getUserMail());
        user.setFio(source.getUserFio());
        user.setRole(source.getRole());

        auditDTO.setUser(user);

        return auditDTO;
    }
}
