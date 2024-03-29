package by.it_academy.product.audit;

import by.it_academy.product.converters.GsonLocalDateTimeToLongSerializer;
import by.it_academy.product.core.dto.AuditDTO;
import by.it_academy.product.core.exception.SendMultiException;
import by.it_academy.product.core.exception.SendSingleException;
import by.it_academy.product.service.UserHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.UUID;

@Aspect
@Component
public class ProductAspect {
    @Value("${audit.url}")
    private String AUDIT_URL;
    private final UserHolder userHolder;
    public ProductAspect(UserHolder userHolder) {
        this.userHolder = userHolder;
    }

    @AfterReturning(value = "@annotation(AspectAudit)", returning = "uuid")
    public void audit(JoinPoint joinPoint, UUID uuid) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AspectAudit annotation = method.getAnnotation(AspectAudit.class);
        try {
            AuditDTO audit = new AuditDTO();
            audit.setUuid(UUID.randomUUID());
            audit.setDtCreate(LocalDateTime.now());
            audit.setUser(userHolder.getUser());
            audit.setText(annotation.action().getDescription());
            audit.setType(annotation.type());
            audit.setId(uuid.toString());

            sendAuditRequest(audit);

        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void sendAuditRequest(AuditDTO auditDTO) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeToLongSerializer())
                .create();
        String json = gson.toJson(auditDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AUDIT_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = send.statusCode();
            String body = send.body();

            if(statusCode >= 400 && statusCode < 500) {
                throw new SendMultiException(body);
            } else if (statusCode >= 500) {
                throw new SendSingleException(body);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
