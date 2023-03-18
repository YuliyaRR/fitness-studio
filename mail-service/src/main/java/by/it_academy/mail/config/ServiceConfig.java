package by.it_academy.mail.config;

import by.it_academy.mail.repositories.api.MailEntityRepository;
import by.it_academy.mail.service.MailService;
import by.it_academy.mail.service.api.IMailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.javamail.JavaMailSender;
@Configuration
public class ServiceConfig {
    @Bean
    public IMailService mailService(MailEntityRepository mailEntityRepository,
                                    JavaMailSender javaMailSender,
                                    ConversionService conversionService) {
        return new MailService(mailEntityRepository, javaMailSender, conversionService);
    }

}
