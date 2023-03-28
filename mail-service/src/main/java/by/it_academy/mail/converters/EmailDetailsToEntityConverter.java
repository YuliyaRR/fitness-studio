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
        return new MailEntity(UUID.randomUUID(),
                source.getMsgBody(),
                source.getRecipient(),
                MailTheme.getMailThemeByDescription(source.getSubject()),
                false,0, 5);
    }
}
