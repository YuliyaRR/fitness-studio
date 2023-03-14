package by.it_academy.user.repositories.api;

import by.it_academy.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, UUID>, PagingAndSortingRepository<UserEntity, UUID> {
    boolean existsByMail(String mail);


}
