package by.it_academy.fitnessstudio.repositories.api;

import by.it_academy.fitnessstudio.entity.UserEntity;
import org.springframework.data.repository.Repository;

import java.util.UUID;


public interface AuthEntityRepository extends Repository<UserEntity, UUID> {

}
