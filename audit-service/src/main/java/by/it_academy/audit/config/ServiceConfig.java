package by.it_academy.audit.config;

import by.it_academy.audit.repositories.api.AuditEntityRepository;
import by.it_academy.audit.service.AuditService;
import by.it_academy.audit.service.api.IAuditService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

@Configuration
public class ServiceConfig {
    @Bean
    public IAuditService auditService(AuditEntityRepository repository, ConversionService conversionService) {
        return new AuditService(repository, conversionService);
    }
}
