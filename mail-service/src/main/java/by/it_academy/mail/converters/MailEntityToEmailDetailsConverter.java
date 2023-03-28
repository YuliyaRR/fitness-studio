package by.it_academy.mail.converters;

import by.it_academy.mail.core.mail.EmailDetails;
import by.it_academy.mail.entity.MailEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MailEntityToEmailDetailsConverter implements Converter<MailEntity, EmailDetails> {
    @Override
    public EmailDetails convert(MailEntity source) {

        return new EmailDetails(source.getTheme().getDescription(), source.getEmail(), source.getMessage());
    }
}
