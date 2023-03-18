package by.it_academy.mail.service.api;

import by.it_academy.mail.core.mail.EmailDetails;
import by.it_academy.mail.entity.MailEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IMailService {

    void sendSimpleEmail(@NotNull @Valid EmailDetails emailDetails);

    void save(MailEntity mailEntity);

}
