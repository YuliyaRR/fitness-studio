package by.it_academy.fitnessstudio.repositories.api;

import by.it_academy.fitnessstudio.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, UUID>, PagingAndSortingRepository<UserEntity, UUID> {
    boolean existsByMail(String mail);


}
