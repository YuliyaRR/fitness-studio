package by.it_academy.user.service;

import by.it_academy.user.audit.AspectAudit;
import by.it_academy.user.audit.AuditAction;
import by.it_academy.user.audit.EssenceType;
import by.it_academy.user.core.dto.OnePage;
import by.it_academy.user.core.dto.error.ErrorCode;
import by.it_academy.user.core.dto.user.User;
import by.it_academy.user.core.dto.user.UserCreateDTO;
import by.it_academy.user.core.exception.ConversionTimeException;
import by.it_academy.user.core.exception.InvalidInputServiceSingleException;
import by.it_academy.user.entity.RoleEntity;
import by.it_academy.user.entity.StatusEntity;
import by.it_academy.user.entity.UserEntity;
import by.it_academy.user.repositories.api.UserEntityRepository;
import by.it_academy.user.service.api.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Validated
public class UserService implements IUserService {
    private final UserEntityRepository repository;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserEntityRepository repository,
                       ConversionService conversionService,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @AspectAudit(action = AuditAction.CREATE, type = EssenceType.USER)
    public UUID save(@NotNull @Valid UserCreateDTO userCreateDTO) {
        checkUniqueMail(userCreateDTO);

        if(!conversionService.canConvert(UserCreateDTO.class, UserEntity.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        UserEntity userEntity = conversionService.convert(userCreateDTO, UserEntity.class);

        String password = userEntity.getPassword();
        password = passwordEncoder.encode(password);
        userEntity.setPassword(password);

        UserEntity save = repository.save(userEntity);
        return save.getUuid();
    }

    @Override
    public OnePage<User> getUsersPage(@NotNull Pageable pageable) {
        Page<UserEntity> all = repository.findAll(pageable);

        if(!conversionService.canConvert(UserEntity.class, User.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

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
    public User getUserInfo(@NotNull UUID uuid) {

        UserEntity userEntity = repository.findById(uuid)
                .orElseThrow(() -> new InvalidInputServiceSingleException("User with this uuid was not found in the database", ErrorCode.ERROR));

        if(!conversionService.canConvert(UserEntity.class, User.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        return conversionService.convert(userEntity, User.class);
    }

    @Override
    @AspectAudit(action = AuditAction.UPDATED, type = EssenceType.USER)
    public UUID update(@NotNull UUID uuid, @NotNull LocalDateTime dtUpdate, @NotNull @Valid UserCreateDTO userCreateDTO) {

        String mail = userCreateDTO.getMail();
        String password = userCreateDTO.getPassword();

        UserEntity userEntity = repository.findById(uuid)
                .orElseThrow(() -> new InvalidInputServiceSingleException("User with this uuid was not found in the database", ErrorCode.ERROR));

        if(userEntity.getDtUpdate().equals(dtUpdate)) {
            if(!userEntity.getMail().equals(mail)) {
                checkUniqueMail(userCreateDTO);
            }
            userEntity.setMail(mail);
            userEntity.setFio(userCreateDTO.getFio());
            userEntity.setRole(new RoleEntity(userCreateDTO.getRole()));
            userEntity.setStatus(new StatusEntity(userCreateDTO.getStatus()));
            userEntity.setPassword(passwordEncoder.encode(password));
            repository.save(userEntity);
        } else {
            throw new InvalidInputServiceSingleException("User with this version was not found in the database", ErrorCode.ERROR);
        }
        return userEntity.getUuid();
    }

    private void checkUniqueMail(UserCreateDTO userCreateDTO) {
        if(repository.existsByMail(userCreateDTO.getMail())) {
            throw new InvalidInputServiceSingleException("Non-unique email", ErrorCode.ERROR);
        }
    }
}
