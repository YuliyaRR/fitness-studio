package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.*;
import by.it_academy.fitnessstudio.core.dto.user.User;
import by.it_academy.fitnessstudio.core.dto.user.UserCreateDTO;
import by.it_academy.fitnessstudio.core.dto.user.UserRole;
import by.it_academy.fitnessstudio.core.dto.user.UserStatus;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceSingleException;
import by.it_academy.fitnessstudio.core.exception.NotFoundDataBaseException;
import by.it_academy.fitnessstudio.entity.UserEntity;
import by.it_academy.fitnessstudio.repositories.api.UserEntityRepository;
import by.it_academy.fitnessstudio.service.api.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserService implements IUserService {
    private final UserEntityRepository repository;
    private final ConversionService conversionService;

    public UserService(UserEntityRepository repository, ConversionService conversionService) {
        this.repository = repository;
        this.conversionService = conversionService;
    }

    @Override
    public UserEntity save(UserCreateDTO userCreateDTO) {
        if(userCreateDTO == null) {
            throw new InvalidInputServiceSingleException("User information not submitted for create");
        }

        validateUser(userCreateDTO);
        checkUniqueMail(userCreateDTO);

        return repository.save(conversionService.convert(userCreateDTO, UserEntity.class));
    }

    @Override
    public OnePage<User> getUsersPage(Integer page, Integer size) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException("structured_error");

        if(page == null || page < 0) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Invalid field value. Field must be 0 or greater", "page"));
        }

        if(size == null || size <= 0) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Invalid field value. Field must be greater than 0", "size"));
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }

        Page<UserEntity> all = repository.findAll(PageRequest.of(page, size));

        List<User> content = all.get().map(entity -> conversionService.convert(entity, User.class)).toList();

        int number = all.getNumber();
        int sizePage = all.getSize();
        int totalPages = all.getTotalPages();
        long totalElements = all.getTotalElements();
        boolean first = all.isFirst();
        int numberOfElements = all.getNumberOfElements();
        boolean last = all.isLast();

        //TODO билдер OnePage

        OnePage<User> onePage = new OnePage<>(number, sizePage, totalPages, totalElements, first, numberOfElements, last, content);

        return onePage;
    }

    @Override
    public User getUserInfo(UUID uuid) {

        if(uuid == null) {
            throw new InvalidInputServiceSingleException("UUID not entered");
        }

        Optional<UserEntity> userById = repository.findById(uuid);

        if (userById.isEmpty()) {
            throw new InvalidInputServiceSingleException("User with this uuid was not found in the database");
        }

        UserEntity userEntity = userById.get();

        return conversionService.convert(userEntity, User.class);
    }

    @Override
    public void update(UUID uuid, Long dtUpdate, UserCreateDTO userCreateDTO) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException("Invalid input");

        if(uuid == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("UUID not entered", "uuid"));
        }

        if(dtUpdate == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("No latest update date", "dt_update"));
        }

        if(userCreateDTO == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("User information not submitted for update", "user"));
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }

        validateUser(userCreateDTO);

        String mail = userCreateDTO.getMail();

        Optional<UserEntity> userById = repository.findById(uuid);//почему подсвечивает? если будет null, то выбросит ex выше??

        if (userById.isEmpty()) {
            throw new InvalidInputServiceSingleException("User with this uuid was not found in the database");
        }

        UserEntity userEntity = userById.get();

        Long timeUpdate = conversionService.convert(userEntity.getDtUpdate(), Long.class);

        if(timeUpdate.equals(dtUpdate)) {
            if(!userEntity.getMail().equals(mail)) {
                checkUniqueMail(userCreateDTO);
            }
            userEntity.setMail(userCreateDTO.getMail());
            userEntity.setFio(userCreateDTO.getFio());
            userEntity.setRole(userCreateDTO.getRole());
            userEntity.setStatus(userCreateDTO.getStatus());
            userEntity.setPassword(userCreateDTO.getPassword());
            repository.save(userEntity);//?save??

        } else {
            throw new InvalidInputServiceSingleException("User with this version was not found in the database");
        }
    }

    private void validateUser(UserCreateDTO userCreateDTO) {
        InvalidInputServiceMultiException exception = new InvalidInputServiceMultiException("Invalid input");

        String mail = userCreateDTO.getMail();

        if(mail == null || mail.isBlank() || mail.isEmpty()){
            exception.addSuppressed(new InvalidInputServiceMultiException("Email not entered", "email"));
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`{|},~\\-]+(?:\\\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~\\-]+)*@+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?+\\.+[a-zA-Z]*$");

        if (mail != null && !pattern.matcher(mail).matches()) {
            exception.addSuppressed(new InvalidInputServiceMultiException("Email not validated", "email"));
        }

        String fullName = userCreateDTO.getFio();
        if(fullName == null || fullName.isBlank() || fullName.isEmpty()){
            exception.addSuppressed(new InvalidInputServiceMultiException("Name not entered", "fio"));
        }

        UserRole role = userCreateDTO.getRole();

        if(role == null){
            exception.addSuppressed(new InvalidInputServiceMultiException("User role not entered", "role"));
        }

        if (role != null) {//TODO: если больше нет ролей, то удалить
            if (!role.equals(UserRole.ADMIN) && !role.equals(UserRole.USER)) {
                exception.addSuppressed(new InvalidInputServiceMultiException("User role is invalid", "role"));
            }
        }

        UserStatus status = userCreateDTO.getStatus();

        if(status == null) {
            exception.addSuppressed(new InvalidInputServiceMultiException("User status not entered", "status"));
        }

        if(status != null) {
            if (!status.equals(UserStatus.WAITING_ACTIVATION) && !status.equals(UserStatus.ACTIVATED)) {
                exception.addSuppressed(new InvalidInputServiceMultiException("User status is invalid", "status"));
            }
        }

        String password = userCreateDTO.getPassword();

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

        //TODO: добавить валидацию по обязательному содержанию цифр, букв, спецсимволов

    }

    private void checkUniqueMail(UserCreateDTO userCreateDTO) {
        if(repository.existsByMail(userCreateDTO.getMail())) {
            throw new InvalidInputServiceSingleException("Non-unique email");
        }
    }
}
