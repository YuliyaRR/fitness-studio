package by.it_academy.user.repositories.api;

import by.it_academy.user.entity.MailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailEntityRepository extends CrudRepository<MailEntity, String> {
}
