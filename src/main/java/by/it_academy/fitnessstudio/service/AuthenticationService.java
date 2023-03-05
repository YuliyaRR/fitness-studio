package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.mail.EmailDetails;
import by.it_academy.fitnessstudio.core.dto.VerificationCode;
import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;
import by.it_academy.fitnessstudio.core.dto.user.*;
import by.it_academy.fitnessstudio.core.exception.*;
import by.it_academy.fitnessstudio.entity.UserEntity;
import by.it_academy.fitnessstudio.repositories.api.UserEntityRepository;
import by.it_academy.fitnessstudio.service.api.IAuthenticationService;
import by.it_academy.fitnessstudio.service.api.IMailService;
import by.it_academy.fitnessstudio.service.api.IUserService;
import by.it_academy.fitnessstudio.service.api.IVerificationService;
import org.springframework.core.convert.ConversionService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class AuthenticationService implements IAuthenticationService {
    private final UserEntityRepository repository;
    private final IMailService mailService;
    private final IUserService userService;
    private final IVerificationService verificationService;
    private final ConversionService conversionService;

    public AuthenticationService(UserEntityRepository repository, IMailService mailService, IUserService userService,
                                 IVerificationService verificationService, ConversionService conversionService) {
        this.repository = repository;
        this.mailService = mailService;
        this.userService = userService;
        this.verificationService = verificationService;
        this.conversionService = conversionService;
    }

    @Override
    public void registration(UserRegistration userRegistration) {
        if(userRegistration == null) {
            throw new InvalidInputServiceSingleException("User information not submitted for registration", ErrorCode.ERROR);
        }
        UserCreateDTO convert = conversionService.convert(userRegistration, UserCreateDTO.class);
        userService.save(convert);

        UUID code = UUID.randomUUID();
        verificationService.save(new VerificationCode(userRegistration.getMail(), code));

        EmailDetails details = createMailForVerification(new VerificationCode(userRegistration.getMail(), code));
        mailService.sendSimpleEmail(details);
    }

    @Override
    public void verification(VerificationCode verificationCode) {
        if(verificationCode == null) {
            throw new InvalidInputServiceSingleException("Information for verification not submitted", ErrorCode.ERROR);
        }

        verificationService.verify(verificationCode);

        //UserEntity entity = userService.getByMail(verificationCode.getMail());
        Optional<UserEntity> byMail = repository.getByMail(verificationCode.getMail());

        if (byMail.isEmpty()) {
            throw new InvalidInputServiceSingleException("This email was not found in the database", ErrorCode.ERROR);
        }

        UserEntity userEntity = byMail.get();
        userEntity.setStatus(UserStatus.ACTIVATED);
        repository.save(userEntity);
    }

    @Override
    public void logIn(UserLogin userLogin) {
        if(userLogin == null) {
            throw new InvalidInputServiceSingleException("Information for authentication not submitted", ErrorCode.ERROR);
        }

        validateLogIn(userLogin);

        Optional<UserEntity> byMail = repository.getByMail(userLogin.getMail());

        if (byMail.isEmpty()) {
            throw new InvalidInputServiceSingleException("Wrong email entered", ErrorCode.ERROR);
        }

        UserEntity userEntity = byMail.get();
        String passwordDB = userEntity.getPassword();
        if(!Objects.equals(userLogin.getPassword(), passwordDB)){
            throw new InvalidLoginException("Wrong password entered", ErrorCode.ERROR);
        }
    }

    @Override
    //предположительный вариант, параметр поиска не известен на данный момент
    public User get(UUID uuid) {
        return userService.getUserInfo(uuid);
    }

    private void validateLogIn(UserLogin userLogin) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);
        String mail = userLogin.getMail();

        if(mail == null || mail.isBlank() || mail.isEmpty()){
            multiException.addSuppressed(new InvalidInputServiceMultiException("Email not entered", "mail"));
        }

        String password = userLogin.getPassword();

        if(password == null || password.isBlank() || password.isEmpty()){
            multiException.addSuppressed(new InvalidInputServiceMultiException("Password not entered", "password"));
        }

        if(password != null) {
            if (password.length() < 8) {
                multiException.addSuppressed(new InvalidInputServiceMultiException("Password can't be less than 8 characters", "password"));
            }
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }

    }

    private EmailDetails createMailForVerification(VerificationCode verificationCode) {
        EmailDetails details = new EmailDetails();
        details.setRecipient(verificationCode.getMail());
        details.setMsgBody(verificationCode.getCode().toString());
        details.setSubject("Verification code");
        return details;
    }
}
