package by.it_academy.user.service.api;

import by.it_academy.user.core.dto.OnePage;
import by.it_academy.user.core.dto.user.User;
import by.it_academy.user.core.dto.user.UserCreateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {
    UUID save(@NotNull @Valid UserCreateDTO userCreateDTO);

    OnePage<User> getUsersPage(@NotNull Pageable pageable);

    User getUserInfo(@NotNull UUID uuid);

    UUID update(@NotNull UUID uuid, @NotNull LocalDateTime dtUpdate, @NotNull @Valid UserCreateDTO userCreateDTO);


}
