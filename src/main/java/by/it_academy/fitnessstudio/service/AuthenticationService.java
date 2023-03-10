package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.mail.EmailDetails;
import by.it_academy.fitnessstudio.core.dto.VerificationCode;
import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;
import by.it_academy.fitnessstudio.core.dto.user.*;
import by.it_academy.fitnessstudio.core.exception.*;
import by.it_academy.fitnessstudio.entity.UserEntity;
import by.it_academy.fitnessstudio.repositories.api.AuthEntityRepository;
import by.it_academy.fitnessstudio.service.api.IAuthenticationService;
import by.it_academy.fitnessstudio.service.api.IMailService;
import by.it_academy.fitnessstudio.service.api.IUserService;
import by.it_academy.fitnessstudio.service.api.IVerificationService;
import by.it_academy.fitnessstudio.validator.api.ValidEmail;
import by.it_academy.fitnessstudio.web.controllers.utils.JwtTokenUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.UUID;
@Validated
public class AuthenticationService implements IAuthenticationService {
    private final AuthEntityRepository repository;
    private final IMailService mailService;
    private final IUserService userService;
    private final IVerificationService verificationService;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthEntityRepository repository,
                                 IMailService mailService,
                                 IUserService userService,
                                 IVerificationService verificationService,
                                 ConversionService conversionService,
                                 PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mailService = mailService;
        this.userService = userService;
        this.verificationService = verificationService;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registration(@NotNull @Valid UserRegistration userRegistration) {
        if(!conversionService.canConvert(UserRegistration.class, UserCreateDTO.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        UserCreateDTO convert = conversionService.convert(userRegistration, UserCreateDTO.class);
        userService.save(convert);

        UUID code = UUID.randomUUID();
        verificationService.save(new VerificationCode(userRegistration.getMail(), code));

        EmailDetails details = createMailForVerification(new VerificationCode(userRegistration.getMail(), code));
        mailService.sendSimpleEmail(details);// при многопоточке изменить на сохранить, а потом выборка из бд отдельным потоком
    }

    @Override
    @Transactional
    public void verification(@NotNull @Valid VerificationCode verificationCode) {
        verificationService.verify(verificationCode);
        repository.setStatusByMail(UserStatus.ACTIVATED, LocalDateTime.now(), verificationCode.getMail());
    }

    @Override
    public String logIn(@NotNull @Valid UserLogin userLogin) {
        UserEntity userEntity = repository.findByMail(userLogin.getMail())
                .orElseThrow(() -> new InvalidLoginException("User with this email doesn't exist", ErrorCode.ERROR));

        String passwordDB = userEntity.getPassword();

        if(!passwordEncoder.matches(userLogin.getPassword(), passwordDB)){
            throw new InvalidLoginException("Wrong password entered", ErrorCode.ERROR);
        }

        return JwtTokenUtil.generateAccessToken(userEntity);
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
}
