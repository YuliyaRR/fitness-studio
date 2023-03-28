package by.it_academy.mail.repositories.api;

import by.it_academy.mail.core.mail.MailTheme;
import by.it_academy.mail.entity.MailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MailEntityRepository extends CrudRepository<MailEntity, String> {
    List<MailEntity> findByIsSendFalse();
    Optional<MailEntity> findByEmailAndThemeAndIsSendFalse(String email, MailTheme theme);
}
