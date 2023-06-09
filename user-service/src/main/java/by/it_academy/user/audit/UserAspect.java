package by.it_academy.user.audit;

import by.it_academy.user.converters.GsonLocalDateTimeToLongSerializer;
import by.it_academy.user.core.dto.AuditDTO;
import by.it_academy.user.core.dto.user.UserToken;
import by.it_academy.user.core.exception.SendMultiException;
import by.it_academy.user.core.exception.SendSingleException;
import by.it_academy.user.service.UserHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.UUID;
@RequiredArgsConstructor
@Aspect
@Component
public class UserAspect {
    @Value("${audit.url}")
    private String AUDIT_URL;
    private final UserHolder userHolder;

    @AfterReturning(value = "@annotation(AspectAudit)", returning = "uuid")
    public void audit(JoinPoint joinPoint, UUID uuid) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AspectAudit annotation = method.getAnnotation(AspectAudit.class);

        AuditDTO audit = new AuditDTO();
        audit.setUuid(UUID.randomUUID());
        audit.setDtCreate(LocalDateTime.now());
        audit.setText(annotation.action().getDescription());
        audit.setType(annotation.type());
        audit.setId(uuid.toString());

        Authentication authentication = userHolder.getAuthentication();
        UserToken userToken = new UserToken();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            userToken = userHolder.getUser();
        }

        audit.setUser(userToken);

        sendAuditRequest(audit);
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
