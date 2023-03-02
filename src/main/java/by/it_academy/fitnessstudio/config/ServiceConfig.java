package by.it_academy.fitnessstudio.config;

import by.it_academy.fitnessstudio.repositories.api.*;
import by.it_academy.fitnessstudio.service.*;
import by.it_academy.fitnessstudio.service.api.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;


@Configuration
public class ServiceConfig {
    @Bean
    public IAuthenticationService clientService(UserEntityRepository userEntityRepository,
                                                IMailService mailService,
                                                IUserService userService,
                                                ConversionService conversionService){
        return new AuthenticationService(userEntityRepository, mailService, userService, conversionService);
    }

    @Bean
    public IUserService staffRegistration(UserEntityRepository userEntityRepository, ConversionService conversionService) {
        return new UserService(userEntityRepository, conversionService);
    }

    @Bean
    public IMailService mailService(MailEntityRepository mailEntityRepository) {
        return new MailService(mailEntityRepository);
    }

    @Bean
    public IProductService productService(ProductEntityRepository productEntityRepository, ConversionService conversionService) {
        return new ProductService(productEntityRepository, conversionService);
    }

    @Bean
    public IRecipeService recipeService(RecipeEntityRepository recipeEntityRepository, ProductService productService, ConversionService conversionService) {
        return new RecipeService(recipeEntityRepository, productService, conversionService);
    }

}
