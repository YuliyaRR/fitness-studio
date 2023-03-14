package by.it_academy.user.config;

import by.it_academy.user.repositories.api.AuthEntityRepository;
import by.it_academy.user.repositories.api.MailEntityRepository;
import by.it_academy.user.repositories.api.UserEntityRepository;
import by.it_academy.user.repositories.api.VerificationCodeEntityRepository;
import by.it_academy.user.service.AuthenticationService;
import by.it_academy.user.service.MailService;
import by.it_academy.user.service.UserService;
import by.it_academy.user.service.VerificationService;
import by.it_academy.user.service.api.IAuthenticationService;
import by.it_academy.user.service.api.IMailService;
import by.it_academy.user.service.api.IUserService;
import by.it_academy.user.service.api.IVerificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ServiceConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public IAuthenticationService authenticationService(AuthEntityRepository authEntityRepository,
                                                        IMailService mailService,
                                                        IUserService userService,
                                                        IVerificationService verificationService,
                                                        ConversionService conversionService,
                                                        PasswordEncoder passwordEncoder){
        return new AuthenticationService(authEntityRepository, mailService, userService, verificationService, conversionService, passwordEncoder);
    }

    @Bean
    public IVerificationService verificationService(VerificationCodeEntityRepository verificationCodeEntityRepository) {
        return new VerificationService(verificationCodeEntityRepository);
    }

    @Bean
    public IUserService userService(UserEntityRepository userEntityRepository,
                                    ConversionService conversionService,
                                    PasswordEncoder passwordEncoder) {
        return new UserService(userEntityRepository, conversionService, passwordEncoder);
    }

    @Bean
    public IMailService mailService(MailEntityRepository mailEntityRepository,
                                    JavaMailSender javaMailSender) {
        return new MailService(mailEntityRepository, javaMailSender);
    }

}
