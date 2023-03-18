package by.it_academy.mail.service;

import by.it_academy.mail.core.error.ErrorCode;
import by.it_academy.mail.core.exception.ConversionTimeException;
import by.it_academy.mail.core.mail.EmailDetails;
import by.it_academy.mail.entity.MailEntity;
import by.it_academy.mail.repositories.api.MailEntityRepository;
import by.it_academy.mail.service.api.IMailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;

@Validated
public class MailService implements IMailService {
    private final JavaMailSender javaMailSender;
    private final MailEntityRepository repository;
    private final ConversionService conversionService;
    @Value("${spring.mail.username}")
    private String username;

    public MailService(MailEntityRepository repository,
                       JavaMailSender javaMailSender,
                       ConversionService conversionService) {
        this.repository = repository;
        this.javaMailSender = javaMailSender;
        this.conversionService = conversionService;
    }

    @Override
    public void save(MailEntity mailEntity) {
        repository.save(mailEntity);
    }

    @Override
    public void sendSimpleEmail(@NotNull @Valid EmailDetails emailDetails) {
        if(!conversionService.canConvert(EmailDetails.class, MailEntity.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        MailEntity mailEntity = conversionService.convert(emailDetails, MailEntity.class);

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(username);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            mailEntity.setSend(false);
            throw e;
        } finally {
            save(mailEntity);
        }

    }
}
