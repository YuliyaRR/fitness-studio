package by.it_academy.mail.service.api;

import by.it_academy.mail.core.mail.EmailDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IMailService {

    void save(@NotNull @Valid EmailDetails emailDetails);

    List<EmailDetails> getUnsentEmails();

    void sendMail(@NotNull @Valid EmailDetails emailDetails);

    void createSendingMailTask();

}
