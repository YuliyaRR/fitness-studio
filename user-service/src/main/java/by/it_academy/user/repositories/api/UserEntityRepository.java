package by.it_academy.user.repositories.api;

import by.it_academy.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID>{
    boolean existsByMail(String mail);


}
