package by.it_academy.user.web.controllers;

import by.it_academy.user.core.dto.VerificationCode;
import by.it_academy.user.core.dto.user.User;
import by.it_academy.user.core.dto.user.UserLogin;
import by.it_academy.user.core.dto.user.UserRegistration;
import by.it_academy.user.core.dto.user.UserToken;
import by.it_academy.user.service.UserHolder;
import by.it_academy.user.service.api.IAuthenticationService;
import by.it_academy.user.validator.api.ValidEmail;
import by.it_academy.user.web.controllers.utils.JwtTokenHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private final UserHolder userHolder;
    private final JwtTokenHandler jwtTokenHandler;

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody UserRegistration userRegistration) {
        authenticationService.registration(userRegistration);
    }

    @RequestMapping(path = "/verification", method = RequestMethod.GET)
    public void verification(@RequestParam(name = "code") UUID code,
                             @RequestParam(name = "mail") @ValidEmail String mail){
        authenticationService.verification(new VerificationCode(mail, code));
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@Valid @RequestBody UserLogin userLogin){
        UserToken userToken = authenticationService.logIn(userLogin);
        return jwtTokenHandler.generateAccessToken(userToken);
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public User getUser(){
        String mail = userHolder.getUser().getMail();
        return authenticationService.get(mail);
    }


}
