package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.*;
import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;
import by.it_academy.fitnessstudio.core.dto.user.User;
import by.it_academy.fitnessstudio.core.dto.user.UserCreateDTO;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceSingleException;
import by.it_academy.fitnessstudio.entity.UserEntity;
import by.it_academy.fitnessstudio.repositories.api.UserEntityRepository;
import by.it_academy.fitnessstudio.service.api.IUserService;
import by.it_academy.fitnessstudio.validator.api.IValidator;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService implements IUserService {
    private final UserEntityRepository repository;
    private final ConversionService conversionService;
    private final IValidator<UserCreateDTO> validator;


    public UserService(UserEntityRepository repository, ConversionService conversionService, IValidator<UserCreateDTO> validator) {
        this.repository = repository;
        this.conversionService = conversionService;
        this.validator = validator;
    }

    @Override
    public UserEntity save(UserCreateDTO userCreateDTO) {
        if(userCreateDTO == null) {
            throw new InvalidInputServiceSingleException("User information not submitted for create", ErrorCode.ERROR);
        }

        validator.validate(userCreateDTO);
        checkUniqueMail(userCreateDTO);

        return repository.save(conversionService.convert(userCreateDTO, UserEntity.class));
    }

    @Override
    public OnePage<User> getUsersPage(Integer page, Integer size) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);

        if (page == null || page < 0) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Invalid field value. Field must be 0 or greater", "page"));
        }

        if (size == null || size <= 0) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Invalid field value. Field must be greater than 0", "size"));
        }

        //запрашиваемая страница превышает кол-во страниц??

        if (multiException.getSuppressed().length != 0) {
            throw multiException;
        }

        Page<UserEntity> all = repository.findAll(PageRequest.of(page, size));

        List<User> content = all.get().map(entity -> conversionService.convert(entity, User.class)).toList();

        return OnePage.OnePageBuilder.create(content)
                .setNumber(all.getNumber())
                .setSize(all.getSize())
                .setTotalPages(all.getTotalPages())
                .setTotalElements(all.getTotalElements())
                .setFirst(all.isFirst())
                .setNumberOfElements(all.getNumberOfElements())
                .setLast(all.isLast())
                .build();
    }

    @Override
    public User getUserInfo(UUID uuid) {

        if(uuid == null) {
            throw new InvalidInputServiceSingleException("UUID not entered", ErrorCode.ERROR);
        }

        Optional<UserEntity> userById = repository.findById(uuid);

        if (userById.isEmpty()) {
            throw new InvalidInputServiceSingleException("User with this uuid was not found in the database", ErrorCode.ERROR);
        }

        UserEntity userEntity = userById.get();

        return conversionService.convert(userEntity, User.class);
    }

    @Override
    public void update(UUID uuid, LocalDateTime dtUpdate, UserCreateDTO userCreateDTO) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);

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

        validator.validate(userCreateDTO);

        String mail = userCreateDTO.getMail();

        Optional<UserEntity> userById = repository.findById(uuid);

        if (userById.isEmpty()) {
            throw new InvalidInputServiceSingleException("User with this uuid was not found in the database", ErrorCode.ERROR);
        }

        UserEntity userEntity = userById.get();

        if(userEntity.getDtUpdate().equals(dtUpdate)) {
            if(!userEntity.getMail().equals(mail)) {
                checkUniqueMail(userCreateDTO);
            }
            userEntity.setMail(userCreateDTO.getMail());
            userEntity.setFio(userCreateDTO.getFio());
            userEntity.setRole(userCreateDTO.getRole());
            userEntity.setStatus(userCreateDTO.getStatus());
            userEntity.setPassword(userCreateDTO.getPassword());
            repository.save(userEntity);

        } else {
            throw new InvalidInputServiceSingleException("User with this version was not found in the database", ErrorCode.ERROR);
        }
    }

    private void checkUniqueMail(UserCreateDTO userCreateDTO) {
        if(repository.existsByMail(userCreateDTO.getMail())) {
            throw new InvalidInputServiceSingleException("Non-unique email", ErrorCode.ERROR);
        }
    }
}
