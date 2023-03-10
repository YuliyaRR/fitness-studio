package by.it_academy.fitnessstudio.service.api;


import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.user.User;
import by.it_academy.fitnessstudio.core.dto.user.UserCreateDTO;
import by.it_academy.fitnessstudio.entity.UserEntity;
import by.it_academy.fitnessstudio.validator.api.ValidString;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {
    void save(@NotNull @Valid UserCreateDTO userCreateDTO);

    OnePage<User> getUsersPage(@NotNull Pageable pageable);

    User getUserInfo(@NotNull UUID uuid);

    void update(@NotNull UUID uuid, @NotNull LocalDateTime dtUpdate, @NotNull @Valid UserCreateDTO userCreateDTO);

    UserEntity getByMail(@ValidString String mail);

}
