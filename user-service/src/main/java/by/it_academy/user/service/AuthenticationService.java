package by.it_academy.user.service;

import by.it_academy.user.core.dto.VerificationCode;
import by.it_academy.user.core.dto.error.ErrorCode;
import by.it_academy.user.core.dto.mail.EmailDetails;
import by.it_academy.user.core.dto.user.*;
import by.it_academy.user.core.exception.ConversionTimeException;
import by.it_academy.user.core.exception.InvalidLoginException;
import by.it_academy.user.core.exception.SendMailMultiException;
import by.it_academy.user.core.exception.SendMailSingleException;
import by.it_academy.user.entity.UserEntity;
import by.it_academy.user.repositories.api.AuthEntityRepository;
import by.it_academy.user.service.api.IAuthenticationService;
import by.it_academy.user.service.api.IUserService;
import by.it_academy.user.service.api.IVerificationService;
import by.it_academy.user.validator.api.ValidEmail;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.UUID;
@Validated
public class AuthenticationService implements IAuthenticationService {
    private final AuthEntityRepository repository;
    private final IUserService userService;
    private final IVerificationService verificationService;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthEntityRepository repository,
                                 IUserService userService,
                                 IVerificationService verificationService,
                                 ConversionService conversionService,
                                 PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userService = userService;
        this.verificationService = verificationService;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void registration(@NotNull @Valid UserRegistration userRegistration) {
        if(!conversionService.canConvert(UserRegistration.class, UserCreateDTO.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        UserCreateDTO convert = conversionService.convert(userRegistration, UserCreateDTO.class);
        userService.save(convert);

        UUID code = UUID.randomUUID();
        VerificationCode verificationCode = new VerificationCode(userRegistration.getMail(), code);
        verificationService.save(verificationCode);

        EmailDetails details = createMailForVerification(verificationCode);
        sendMailRequest(details);
    }

    @Override
    @Transactional
    public void verification(@NotNull @Valid VerificationCode verificationCode) {
        verificationService.verify(verificationCode);
        repository.setStatusByMail(UserStatus.ACTIVATED, LocalDateTime.now(), verificationCode.getMail());
    }

    @Override
    public UserToken logIn(@NotNull @Valid UserLogin userLogin) {
        UserEntity userEntity = repository.findByMail(userLogin.getMail())
                .orElseThrow(() -> new InvalidLoginException("User with this email doesn't exist", ErrorCode.ERROR));

        String passwordDB = userEntity.getPassword();

        if(!passwordEncoder.matches(userLogin.getPassword(), passwordDB)){
            throw new InvalidLoginException("Wrong password entered", ErrorCode.ERROR);
        }

        if(!conversionService.canConvert(UserEntity.class, UserToken.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        return conversionService.convert(userEntity, UserToken.class);
    }

    @Override
    public User get(@ValidEmail String mail) {
        UserEntity userEntity = repository.findByMail(mail)
                .orElseThrow(() -> new InvalidLoginException("User with this email doesn't exist", ErrorCode.ERROR));

        if(!conversionService.canConvert(UserEntity.class, User.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        return conversionService.convert(userEntity, User.class);
    }

    private EmailDetails createMailForVerification(VerificationCode verificationCode) {
        EmailDetails details = new EmailDetails();
        details.setRecipient(verificationCode.getMail());
        details.setMsgBody(verificationCode.getCode().toString());
        details.setSubject("Verification code");
        return details;
    }

    private void sendMailRequest (EmailDetails emailDetails) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(emailDetails);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://mail-service:8080/mails"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = send.statusCode();
            String body = send.body();

            if(statusCode >= 400 && statusCode < 500) {
                throw new SendMailMultiException(body);
            } else if (statusCode >= 500) {
                throw new SendMailSingleException(body);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
