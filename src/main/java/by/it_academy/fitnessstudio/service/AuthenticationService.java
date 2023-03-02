package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.EmailDetails;
import by.it_academy.fitnessstudio.core.dto.MailCode;
import by.it_academy.fitnessstudio.core.dto.user.*;
import by.it_academy.fitnessstudio.core.exception.*;
import by.it_academy.fitnessstudio.entity.UserEntity;
import by.it_academy.fitnessstudio.repositories.api.UserEntityRepository;
import by.it_academy.fitnessstudio.service.api.IAuthenticationService;
import by.it_academy.fitnessstudio.service.api.IMailService;
import by.it_academy.fitnessstudio.service.api.IUserService;
import org.springframework.core.convert.ConversionService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class AuthenticationService implements IAuthenticationService {
    private final UserEntityRepository repository;
    private final IMailService mailService;
    private final IUserService userService;
    private final ConversionService conversionService;

    public AuthenticationService(UserEntityRepository repository, IMailService mailService, IUserService userService, ConversionService conversionService) {
        this.repository = repository;
        this.mailService = mailService;
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @Override
    public void registration(UserRegistration userRegistration) {
        if(userRegistration == null) {
            throw new InvalidInputServiceSingleException("User information not submitted for registration");
        }
        UserEntity save = userService.save(conversionService.convert(userRegistration, UserCreateDTO.class));
        EmailDetails details = createMailForVerification(new MailCode(userRegistration.getMail(), String.valueOf(save.getCode())));
        mailService.sendSimpleEmail(details);
    }

    @Override
    public void verification(MailCode mailCode) {
        if(mailCode == null) {
            throw new InvalidInputServiceSingleException("Information for verification not submitted");
        }

        validateVerification(mailCode);

        Optional<UserEntity> byMail = repository.getByMail(mailCode.getMail());

        if (byMail.isEmpty()) {
            throw new InvalidInputServiceSingleException("This email was not found in the database");
        }

        UserEntity userEntity = byMail.get();
        Integer verificationCode = userEntity.getCode();
        if(verificationCode.equals(Integer.parseInt(mailCode.getCode()))){
            userEntity.setStatus((UserStatus.ACTIVATED));
            userEntity.setCode(null);
            repository.save(userEntity);
        } else {
            throw new VerificationException("Invalid verification code");
        }

        //с энтити мэил+код
        /*MailCodeEntity mailCodeEntity = mailService.get(mail);

        if (mailCodeEntity.getCode().equals(Long.parseLong(code))){
            Optional<ClientEntity> byMail = repository.getByMail(mail);

            if (byMail.isEmpty()) {
                throw new NotFoundDataBaseException("This email was not found in the database");
            }

             clientEntity = byMail.get();
            clientEntity.setStatus(UserStatus.ACTIVATED);
            repository.save(clientEntity);

        } else {
            throw new VerificationException("Invalid verification code");
        }*/
    }

    @Override
    public void logIn(UserLogin userLogin) {
        if(userLogin == null) {
            throw new InvalidInputServiceSingleException("Information for authentication not submitted");
        }

        validateLogIn(userLogin);

        Optional<UserEntity> byMail = repository.getByMail(userLogin.getMail());

        if (byMail.isEmpty()) {
            throw new InvalidInputServiceSingleException("Wrong email entered");
        }

        UserEntity userEntity = byMail.get();
        String passwordDB = userEntity.getPassword();
        if(!Objects.equals(userLogin.getPassword(), passwordDB)){
            throw new InvalidLoginException("Wrong password entered");
        }
    }

    @Override
    //предположительный вариант, параметр поиска не известен на данный момент
    public User get(UUID uuid) {
        return userService.getUserInfo(uuid);
    }

    private void validateVerification(MailCode mailCode) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException("Invalid input");
        String code = mailCode.getCode();
        String mail = mailCode.getMail();

        if(code == null || code.isBlank() || code.isEmpty()) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Code not entered"));
        }

        if(mail == null || mail.isBlank() || mail.isEmpty()){
            multiException.addSuppressed(new InvalidInputServiceMultiException("Email not entered"));
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }
    }

    private void validateLogIn(UserLogin userLogin) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException("Login failed");
        String mail = userLogin.getMail();

        if(mail == null || mail.isBlank() || mail.isEmpty()){
            multiException.addSuppressed(new InvalidInputServiceMultiException("Email not entered"));
        }

        String password = userLogin.getPassword();

        if(password == null || password.isBlank() || password.isEmpty()){
            multiException.addSuppressed(new InvalidInputServiceMultiException("Password not entered"));
        }

        if(password != null) {
            if (password.length() < 8) {
                multiException.addSuppressed(new InvalidInputServiceMultiException("Password can't be less than 8 characters"));
            }
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }

    }

    private EmailDetails createMailForVerification(MailCode mailCode) {
        EmailDetails details = new EmailDetails();
        details.setRecipient(mailCode.getMail());
        details.setMsgBody(mailCode.getCode());
        details.setSubject("Verification code");
        return details;
    }
}
