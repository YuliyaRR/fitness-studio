package by.it_academy.fitnessstudio.converters;

import by.it_academy.fitnessstudio.core.dto.user.User;
import by.it_academy.fitnessstudio.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDtoConverter implements Converter<UserEntity, User> {
    private final LocalDateTimeToLongMillisConverter localDateTimeToLongMillisConverter;

    public UserEntityToDtoConverter(LocalDateTimeToLongMillisConverter localDateTimeToLongMillisConverter) {
        this.localDateTimeToLongMillisConverter = localDateTimeToLongMillisConverter;
    }

    @Override
    public User convert(UserEntity source) {
        return User.UserBuilder.create()
                .setUuid(source.getUuid())
                .setDtCreate(localDateTimeToLongMillisConverter.convert(source.getDtCreate()))
                .setDtUpdate(localDateTimeToLongMillisConverter.convert(source.getDtUpdate()))
                .setMail(source.getMail())
                .setFio(source.getFio())
                .setRole(source.getRole())
                .setStatus(source.getStatus())
                .build();
    }
}
