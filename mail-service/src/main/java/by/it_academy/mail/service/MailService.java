package by.it_academy.mail.service;

import by.it_academy.mail.core.error.ErrorCode;
import by.it_academy.mail.core.exception.ConversionTimeException;
import by.it_academy.mail.core.exception.InvalidInputServiceSingleException;
import by.it_academy.mail.core.mail.EmailDetails;
import by.it_academy.mail.core.mail.MailTheme;
import by.it_academy.mail.entity.MailEntity;
import by.it_academy.mail.repositories.api.MailEntityRepository;
import by.it_academy.mail.service.api.IMailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Validated
public class MailService implements IMailService {
    private final JavaMailSender javaMailSender;
    private final MailEntityRepository repository;
    private final ConversionService conversionService;
    private final TaskExecutor taskExecutor;
    @Value("${spring.mail.username}")
    private String username;

    public MailService(MailEntityRepository repository,
                       JavaMailSender javaMailSender,
                       ConversionService conversionService, TaskExecutor taskExecutor) {
        this.repository = repository;
        this.javaMailSender = javaMailSender;
        this.conversionService = conversionService;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void save(@NotNull @Valid EmailDetails emailDetails) {
        if(!conversionService.canConvert(EmailDetails.class, MailEntity.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        MailEntity mailEntity = conversionService.convert(emailDetails, MailEntity.class);

        repository.save(mailEntity);
    }

    @Override
    public List<EmailDetails> getUnsentEmails() {
        List<MailEntity> mails = repository.findByIsSendFalse();
        if(mails == null || mails.isEmpty()) {
            return null;
        }

        if(!conversionService.canConvert(MailEntity.class, EmailDetails.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        return mails.stream()
                .map(mailEntity -> conversionService.convert(mailEntity, EmailDetails.class))
                .collect(Collectors.toList());
    }
    @Override
    @Async
    @Scheduled(fixedDelay = 3600000, initialDelay = 120000)
    public void createSendingMailTask(){
        List<EmailDetails> emailDetails = getUnsentEmails();
        if(emailDetails != null) {
            for (EmailDetails emailDetail : emailDetails) {
                Runnable task = () -> sendMail(emailDetail);
                taskExecutor.execute(task);
            }
        }
    }

    @Override
    public void sendMail(@NotNull @Valid EmailDetails emailDetails) {
        String recipient = emailDetails.getRecipient();
        MailEntity entity = repository.findByEmailAndThemeAndIsSendFalse(recipient, MailTheme.getMailThemeByDescription(emailDetails.getSubject()))
                .orElseThrow(() -> new InvalidInputServiceSingleException("Mail doesn't exist", ErrorCode.ERROR));

        int attemptOfSending = entity.getAttemptOfSending();
        int limitOfAttempts = entity.getLimitOfAttempts();

        if(attemptOfSending >= limitOfAttempts) {
            repository.delete(entity);
        } else {
            boolean statusSending = true;

            try {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(username);
                mailMessage.setTo(recipient);
                mailMessage.setText(emailDetails.getMsgBody());
                mailMessage.setSubject(emailDetails.getSubject());

                javaMailSender.send(mailMessage);
            } catch (Exception e) {
                statusSending = false;
                throw e;
            } finally {
                entity.setSend(statusSending);
                entity.setAttemptOfSending(++attemptOfSending);
                repository.save(entity);
            }
        }
    }
}
