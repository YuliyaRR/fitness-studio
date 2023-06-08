package by.it_academy.user.converters;

import by.it_academy.user.core.dto.user.User;
import by.it_academy.user.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDtoConverter implements Converter<UserEntity, User> {

    @Override
    public User convert(UserEntity source) {
        return User.builder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setMail(source.getMail())
                .setFio(source.getFio())
                .setRole(source.getRole().getRole())
                .setStatus(source.getStatus().getStatus())
                .build();
    }
}
