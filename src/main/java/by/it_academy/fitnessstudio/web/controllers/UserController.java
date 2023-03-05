package by.it_academy.fitnessstudio.web.controllers;

import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.user.User;
import by.it_academy.fitnessstudio.core.dto.user.UserCreateDTO;
import by.it_academy.fitnessstudio.service.api.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserCreateDTO userCreateDTO) {
        userService.save(userCreateDTO);
    }

    @RequestMapping(method = RequestMethod.GET)
    public OnePage<User> getUsersPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                @RequestParam(name = "size", defaultValue = "20") Integer size) {

        return userService.getUsersPage(page, size);
    }

    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    public User getUserInfo(@PathVariable("uuid") UUID uuid){
        return userService.getUserInfo(uuid);
    }

    @RequestMapping(path = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT)
    public void updateUserInfo(@PathVariable("uuid") UUID uuid,
                           @PathVariable("dt_update") LocalDateTime dtUpdate,
                           @RequestBody UserCreateDTO userCreate){
        userService.update(uuid, dtUpdate, userCreate);
    }








}
