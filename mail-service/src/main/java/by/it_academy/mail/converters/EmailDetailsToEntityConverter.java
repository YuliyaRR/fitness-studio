package by.it_academy.mail.converters;

import by.it_academy.mail.core.mail.EmailDetails;
import by.it_academy.mail.core.mail.MailTheme;
import by.it_academy.mail.entity.MailEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailDetailsToEntityConverter implements Converter<EmailDetails, MailEntity> {
    @Override
    public MailEntity convert(EmailDetails source) {
        return MailEntity.builder()
                .uuid(UUID.randomUUID())
                .message(source.getMsgBody())
                .email(source.getRecipient())
                .theme(MailTheme.getMailThemeByDescription(source.getSubject()))
                .isSend(false)
                .attemptOfSending(0)
                .limitOfAttempts(5)
                .build();
    }
}
