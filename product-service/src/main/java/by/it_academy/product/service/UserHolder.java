package by.it_academy.product.service;

import by.it_academy.product.core.dto.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
@Component
public class UserHolder {
    public User getUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
