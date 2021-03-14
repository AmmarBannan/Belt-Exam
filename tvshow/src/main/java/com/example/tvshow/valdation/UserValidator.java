package com.example.tvshow.valdation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.tvshow.models.User;
import com.example.tvshow.repositories.UserRepo;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserRepo uRepo;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	User user = (User) target;

        if(this.uRepo.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Unique");
        }

        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirmation", "Match");
        }
    }
}