package by.it_academy.fitnessstudio.service.api;


import by.it_academy.fitnessstudio.core.dto.EmailDetails;
import by.it_academy.fitnessstudio.entity.MailEntity;

public interface IMailService {

   // void save(MailCodeEntity mailCodeEntity);

    void sendSimpleEmail(EmailDetails emailDetails);

   // MailCodeEntity get(String mail);
}
