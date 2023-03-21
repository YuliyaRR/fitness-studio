package by.it_academy.user.repositories.api;

import by.it_academy.user.entity.UserEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;
@org.springframework.stereotype.Repository
public interface AuthEntityRepository extends Repository<UserEntity, UUID> {
    Optional<UserEntity> findByMail(String mail);

}
