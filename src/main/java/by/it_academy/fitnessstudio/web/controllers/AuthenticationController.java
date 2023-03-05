package by.it_academy.fitnessstudio.web.controllers;

import by.it_academy.fitnessstudio.core.dto.VerificationCode;
import by.it_academy.fitnessstudio.core.dto.user.User;
import by.it_academy.fitnessstudio.core.dto.user.UserLogin;
import by.it_academy.fitnessstudio.core.dto.user.UserRegistration;
import by.it_academy.fitnessstudio.service.api.IAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@RequestBody UserRegistration userRegistration) {
        authenticationService.registration(userRegistration);
    }

    @RequestMapping(path = "/verification", method = RequestMethod.GET)
    public void verification(@RequestParam(name = "code") UUID code,
                             @RequestParam(name = "mail") String mail){

        authenticationService.verification(new VerificationCode(mail, code));
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public void login(@RequestBody UserLogin userLogin){
        authenticationService.logIn(userLogin);
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public User getUser(){
        UUID uuid = UUID.fromString("89fc6b03-6152-4061-9da6-dc046edc20f5");
        return authenticationService.get(uuid);
    }


}
