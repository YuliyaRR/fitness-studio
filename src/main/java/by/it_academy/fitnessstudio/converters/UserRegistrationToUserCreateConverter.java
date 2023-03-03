package by.it_academy.fitnessstudio.converters;

import by.it_academy.fitnessstudio.core.dto.user.UserCreateDTO;
import by.it_academy.fitnessstudio.core.dto.user.UserRegistration;
import by.it_academy.fitnessstudio.core.dto.user.UserRole;
import by.it_academy.fitnessstudio.core.dto.user.UserStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationToUserCreateConverter implements Converter<UserRegistration, UserCreateDTO> {
    @Override
    public UserCreateDTO convert(UserRegistration source) {
        return UserCreateDTO.UserCreateBuilder.create()
                .setMail(source.getMail())
                .setFio(source.getFio())
                .setPassword(source.getPassword())
                .setRole(UserRole.USER)
                .setStatus(UserStatus.WAITING_ACTIVATION)
                .build();
    }
}
