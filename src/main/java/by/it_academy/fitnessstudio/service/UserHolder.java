package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
@Component
public class UserHolder {
    public User getUser(){
        return (by.it_academy.fitnessstudio.core.dto.user.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
