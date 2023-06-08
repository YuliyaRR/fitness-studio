package by.it_academy.user.converters;

import by.it_academy.user.core.dto.user.UserCreateDTO;
import by.it_academy.user.entity.RoleEntity;
import by.it_academy.user.entity.StatusEntity;
import by.it_academy.user.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
@Component
public class UserCreateDtoToEntityConverter implements Converter<UserCreateDTO, UserEntity> {
    @Override
    public UserEntity convert(UserCreateDTO source) {
        LocalDateTime dtCreate = LocalDateTime.now();

        return UserEntity.builder()
                .setUuid(UUID.randomUUID())
                .setMail(source.getMail())
                .setFio(source.getFio())
                .setRole(new RoleEntity(source.getRole()))
                .setStatus(new StatusEntity(source.getStatus()))
                .setPassword(source.getPassword())
                .setDtCreate(dtCreate)
                .setDtUpdate(dtCreate)
                .build();
    }
}
