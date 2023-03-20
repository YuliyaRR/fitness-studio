package by.it_academy.audit.service;

import by.it_academy.audit.core.dto.UserToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
@Component
public class UserHolder {
    public UserToken getUser(){
        return (UserToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
