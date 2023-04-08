package by.it_academy.audit.converters;

import by.it_academy.audit.core.dto.AuditDTO;
import by.it_academy.audit.core.dto.UserToken;
import by.it_academy.audit.entity.AuditEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

        UserToken user;

        UUID userUUID = source.getUserUUID();
        String userMail = source.getUserMail();
        String userFio = source.getUserFio();
        String role = source.getRole();

        if(userUUID == null && userMail == null && userFio == null && role == null){
           user = null;
        } else {
            user = new UserToken();

            user.setUuid(userUUID);
            user.setMail(userMail);
            user.setFio(userFio);
            user.setRole(role);
        }

        auditDTO.setUser(user);

        return auditDTO;
    }
}
