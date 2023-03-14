package by.it_academy.user.service;

import by.it_academy.user.core.dto.error.ErrorCode;
import by.it_academy.user.core.dto.mail.EmailDetails;
import by.it_academy.user.core.exception.InvalidInputServiceSingleException;
import by.it_academy.user.entity.MailEntity;
import by.it_academy.user.repositories.api.MailEntityRepository;
import by.it_academy.user.service.api.IMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.UUID;

public class MailService implements IMailService {
    private final JavaMailSender javaMailSender;
    private final MailEntityRepository repository;
    @Value("${spring.mail.username}")
    private String username;//final.project.jd2@gmail.com;

    //private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public MailService(MailEntityRepository repository, JavaMailSender javaMailSender) {
        this.repository = repository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void save(EmailDetails emailDetails) {
        repository.save(new MailEntity(UUID.randomUUID(),
                emailDetails.getMsgBody(),
                emailDetails.getRecipient(),
                false,0, 5));
    }

    @Override
    public void sendSimpleEmail(EmailDetails emailDetails) {
        //TODO: написать отправку почту в параллельном потоке
        if(emailDetails == null) {
            throw new InvalidInputServiceSingleException("Mail for sending is not creating", ErrorCode.ERROR);
        }
        //валидация дто
        save(emailDetails);

        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(username);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw e;
        }

    }
}
