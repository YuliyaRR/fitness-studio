package by.it_academy.product.config;

import by.it_academy.product.core.dto.recipe.RecipeCreate;
import by.it_academy.product.repositories.api.ProductEntityRepository;
import by.it_academy.product.repositories.api.RecipeEntityRepository;
import by.it_academy.product.service.ProductService;
import by.it_academy.product.service.RecipeService;
import by.it_academy.product.service.api.IProductService;
import by.it_academy.product.service.api.IRecipeService;
import by.it_academy.product.validator.api.IValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ServiceConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
                                        IValidator<RecipeCreate> validator) {
        return new RecipeService(recipeEntityRepository, productService, conversionService, validator);
    }

}
