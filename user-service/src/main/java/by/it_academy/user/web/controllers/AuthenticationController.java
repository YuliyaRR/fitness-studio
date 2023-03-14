package by.it_academy.user.web.controllers;

import by.it_academy.user.core.dto.VerificationCode;
import by.it_academy.user.core.dto.user.User;
import by.it_academy.user.core.dto.user.UserLogin;
import by.it_academy.user.core.dto.user.UserRegistration;
import by.it_academy.user.service.UserHolder;
import by.it_academy.user.service.api.IAuthenticationService;
import by.it_academy.user.validator.api.ValidEmail;
import by.it_academy.user.web.controllers.utils.JwtTokenUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Validated
@RestController
@RequestMapping(path = "/users")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private final UserHolder userHolder;

    public AuthenticationController(IAuthenticationService authenticationService, UserHolder userHolder) {
        this.authenticationService = authenticationService;
        this.userHolder = userHolder;
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody UserRegistration userRegistration) {
        authenticationService.registration(userRegistration);
    }

    @RequestMapping(path = "/verification", method = RequestMethod.GET)
    public void verification(@RequestParam(name = "code") @NotNull UUID code,
                             @RequestParam(name = "mail") @NotNull @ValidEmail String mail){
        authenticationService.verification(new VerificationCode(mail, code));
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@Valid @RequestBody UserLogin userLogin){

        User user = authenticationService.logIn(userLogin);

        return JwtTokenUtil.generateAccessToken(user);
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public User getUser(){
        String mail = userHolder.getUser().getMail();
        return authenticationService.get(mail);
    }


}
