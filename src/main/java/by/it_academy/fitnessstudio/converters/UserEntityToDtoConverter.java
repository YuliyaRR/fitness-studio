package by.it_academy.fitnessstudio.converters;

import by.it_academy.fitnessstudio.core.dto.user.User;
import by.it_academy.fitnessstudio.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDtoConverter implements Converter<UserEntity, User> {

    @Override
    public User convert(UserEntity source) {
        return User.UserBuilder.create()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setMail(source.getMail())
                .setFio(source.getFio())
                .setRole(source.getRole())
                .setStatus(source.getStatus())
                .build();
    }
}
