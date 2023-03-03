package by.it_academy.fitnessstudio.validator;

import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;
import by.it_academy.fitnessstudio.core.dto.user.UserCreateDTO;
import by.it_academy.fitnessstudio.core.dto.user.UserRole;
import by.it_academy.fitnessstudio.core.dto.user.UserStatus;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.validator.api.IValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@Component
public class UserCreateValidator implements IValidator<UserCreateDTO> {
    @Override
    public void validate(UserCreateDTO item) {
        InvalidInputServiceMultiException exception = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);

        String mail = item.getMail();

        if(mail == null || mail.isBlank() || mail.isEmpty()){
            exception.addSuppressed(new InvalidInputServiceMultiException("Email not entered", "mail"));
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`{|},~\\-]+(?:\\\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~\\-]+)*@+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?+\\.+[a-zA-Z]*$");

        if (mail != null && !pattern.matcher(mail).matches()) {
            exception.addSuppressed(new InvalidInputServiceMultiException("Email not validated", "mail"));
        }

        String fullName = item.getFio();
        if(fullName == null || fullName.isBlank() || fullName.isEmpty()){
            exception.addSuppressed(new InvalidInputServiceMultiException("Name not entered", "fio"));
        }

        UserRole role = item.getRole();

        if(role == null){
            exception.addSuppressed(new InvalidInputServiceMultiException("User role not entered", "role"));
        }

        if (role != null) {//TODO: если больше нет ролей, то удалить
            if (!role.equals(UserRole.ADMIN) && !role.equals(UserRole.USER)) {
                exception.addSuppressed(new InvalidInputServiceMultiException("User role is invalid", "role"));
            }
        }

        UserStatus status = item.getStatus();

        if(status == null) {
            exception.addSuppressed(new InvalidInputServiceMultiException("User status not entered", "status"));
        }

        if(status != null) {
            if (!status.equals(UserStatus.WAITING_ACTIVATION) && !status.equals(UserStatus.ACTIVATED)) {
                exception.addSuppressed(new InvalidInputServiceMultiException("User status is invalid", "status"));
            }
        }

        String password = item.getPassword();

        if(password == null || password.isBlank() || password.isEmpty()){
            exception.addSuppressed(new InvalidInputServiceMultiException("Password not entered", "password"));
        }

        if(password != null) {
            if (password.length() < 8) {
                exception.addSuppressed(new InvalidInputServiceMultiException("Password can't be less than 8 characters", "password"));
            }
        }

        if(exception.getSuppressed().length != 0) {
            throw exception;
        }

    }
}
