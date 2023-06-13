package by.it_academy.audit.converters;

import by.it_academy.audit.core.dto.AuditDTO;
import by.it_academy.audit.core.dto.UserRole;
import by.it_academy.audit.core.dto.UserToken;
import by.it_academy.audit.entity.AuditEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuditEntityToAuditDtoConverter implements Converter<AuditEntity, AuditDTO> {
    @Override
    public AuditDTO convert(AuditEntity source) {
        AuditDTO.AuditDTOBuilder builder = AuditDTO.builder();

        builder.uuid(source.getUuid());
        builder.dtCreate(source.getDtCreate());
        builder.text(source.getText());
        builder.type(source.getType());
        builder.id(source.getId());

        UserToken user;

        UUID userUUID = source.getUserUUID();
        String userMail = source.getUserMail();
        String userFio = source.getUserFio();
        UserRole role = source.getRole();

        if(userUUID == null && userMail == null && userFio == null && role == null){
           user = null;
        } else {
            user = new UserToken();

            user.setUuid(userUUID);
            user.setMail(userMail);
            user.setFio(userFio);
            user.setRole(role);
        }

        builder.user(user);

        return builder.build();
    }
}
