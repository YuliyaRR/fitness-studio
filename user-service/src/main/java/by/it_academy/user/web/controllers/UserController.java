package by.it_academy.user.web.controllers;

import by.it_academy.user.core.dto.OnePage;
import by.it_academy.user.core.dto.user.User;
import by.it_academy.user.core.dto.user.UserCreateDTO;
import by.it_academy.user.service.api.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        userService.save(userCreateDTO);
    }

    @RequestMapping(method = RequestMethod.GET)
    public OnePage<User> getUsersPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                      @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getUsersPage(pageable);
    }

    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    public User getUserInfo(@PathVariable("uuid") UUID uuid){
        return userService.getUserInfo(uuid);
    }

    @RequestMapping(path = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT)
    public void updateUserInfo(@PathVariable("uuid") UUID uuid,
                               @PathVariable("dt_update") @Past LocalDateTime dtUpdate,
                               @Valid @RequestBody UserCreateDTO userCreate){
        userService.update(uuid, dtUpdate, userCreate);
    }








}
