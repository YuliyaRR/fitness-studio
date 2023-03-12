package by.it_academy.fitnessstudio.config;

import by.it_academy.fitnessstudio.core.dto.recipe.RecipeCreate;
import by.it_academy.fitnessstudio.repositories.api.*;
import by.it_academy.fitnessstudio.service.*;
import by.it_academy.fitnessstudio.service.api.*;

import by.it_academy.fitnessstudio.validator.api.IValidator;
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

    @Bean
    public IProductService productService(ProductEntityRepository productEntityRepository,
                                          ConversionService conversionService) {
        return new ProductService(productEntityRepository, conversionService);
    }

    @Bean
    public IRecipeService recipeService(RecipeEntityRepository recipeEntityRepository,
                                        IProductService productService,
                                        ConversionService conversionService,
                                        IValidator<RecipeCreate>validator) {
        return new RecipeService(recipeEntityRepository, productService, conversionService, validator);
    }

}
