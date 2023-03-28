package by.it_academy.mail.config;

import by.it_academy.mail.repositories.api.MailEntityRepository;
import by.it_academy.mail.service.MailService;
import by.it_academy.mail.service.api.IMailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
@EnableAsync
public class ServiceConfig {
    @Bean
    public IMailService mailService(MailEntityRepository mailEntityRepository,
                                    JavaMailSender javaMailSender,
                                    ConversionService conversionService, TaskExecutor taskExecutor) {
        return new MailService(mailEntityRepository, javaMailSender, conversionService, taskExecutor);
    }

}
