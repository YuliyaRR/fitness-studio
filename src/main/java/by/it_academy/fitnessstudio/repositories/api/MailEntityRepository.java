package by.it_academy.fitnessstudio.repositories.api;

import by.it_academy.fitnessstudio.entity.MailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailEntityRepository extends CrudRepository<MailEntity, String> {
}
