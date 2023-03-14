package by.it_academy.user.repositories.api;

import by.it_academy.user.core.dto.user.UserStatus;
import by.it_academy.user.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@org.springframework.stereotype.Repository
public interface AuthEntityRepository extends Repository<UserEntity, UUID> {
    @Modifying
    @Query(value = "update app.users set status = :#{#status.name()}, dt_update =:dt_update where mail = :mail",
            nativeQuery = true)
    void setStatusByMail(@Param("status") UserStatus status,
                         @Param("dt_update") LocalDateTime dtUpdate,
                         @Param("mail") String mail);

    Optional<UserEntity> findByMail(String mail);

}
