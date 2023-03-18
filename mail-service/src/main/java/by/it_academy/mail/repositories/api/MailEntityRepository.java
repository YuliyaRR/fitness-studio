package by.it_academy.mail.repositories.api;

import by.it_academy.mail.entity.MailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailEntityRepository extends CrudRepository<MailEntity, String> {
}
