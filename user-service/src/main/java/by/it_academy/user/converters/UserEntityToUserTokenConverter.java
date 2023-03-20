package by.it_academy.user.converters;

import by.it_academy.user.core.dto.user.UserToken;
import by.it_academy.user.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserTokenConverter implements Converter<UserEntity, UserToken> {

    @Override
    public UserToken convert(UserEntity source) {
        return new UserToken(source.getMail(), source.getRole().getRole().name(), source.getFio(), source.getUuid());
    }
}
