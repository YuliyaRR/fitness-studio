package by.it_academy.user.listener;

import by.it_academy.user.core.events.SendEmailRequestEvent;
import by.it_academy.user.core.exception.SendMultiException;
import by.it_academy.user.core.exception.SendSingleException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
public class SendEmailRequestListener {
    @Value("${mail.url}")
    private String MAIL_URL;
    @EventListener
    public void handleSendEmailRequest(SendEmailRequestEvent event) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(event.getEmailDetails());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(MAIL_URL))
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
