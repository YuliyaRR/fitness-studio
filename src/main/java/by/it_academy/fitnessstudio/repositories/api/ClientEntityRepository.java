package by.it_academy.fitnessstudio.repositories.api;

import by.it_academy.fitnessstudio.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientEntityRepository extends CrudRepository<ClientEntity, UUID>, PagingAndSortingRepository<ClientEntity, UUID> {
    Optional<ClientEntity> getByMail(String mail);
}
