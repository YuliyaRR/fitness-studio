package by.it_academy.fitnessstudio.converters;

import by.it_academy.fitnessstudio.core.dto.user.UserCreateDTO;
import by.it_academy.fitnessstudio.core.dto.user.UserStatus;
import by.it_academy.fitnessstudio.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
@Component
public class UserCreateDtoToEntityConverter implements Converter<UserCreateDTO, UserEntity> {

    @Override
    public UserEntity convert(UserCreateDTO source) {
        UserStatus status = source.getStatus();

        UserEntity.UserEntityBuilder userEntityBuilder = UserEntity.UserEntityBuilder.create()
                .setUuid(UUID.randomUUID())
                .setMail(source.getMail())
                .setFio(source.getFio())
                .setRole(source.getRole())
                .setStatus(status)
                .setPassword(source.getPassword())
                .setDtCreate(LocalDateTime.now())
                .setDtUpdate(LocalDateTime.now());

        if(status.equals(UserStatus.WAITING_ACTIVATION)) {
            ThreadLocalRandom rnd = ThreadLocalRandom.current();
            userEntityBuilder.setCode(rnd.nextInt(1000000));
        }

        return userEntityBuilder.build();
    }
}
