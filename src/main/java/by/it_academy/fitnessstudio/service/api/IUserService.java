package by.it_academy.fitnessstudio.service.api;


import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.user.User;
import by.it_academy.fitnessstudio.core.dto.user.UserCreateDTO;
import by.it_academy.fitnessstudio.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {
    UserEntity save(UserCreateDTO userCreateDTO);

    OnePage<User> getUsersPage(Integer page, Integer size);

    User getUserInfo(UUID uuid);

    void update(UUID uuid, LocalDateTime dtUpdate, UserCreateDTO userCreateDTO);

}
