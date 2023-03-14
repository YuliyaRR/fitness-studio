package by.it_academy.user.service.api;

import by.it_academy.user.core.dto.mail.EmailDetails;

public interface IMailService {

    void sendSimpleEmail(EmailDetails emailDetails);

    void save(EmailDetails emailDetails);

}
