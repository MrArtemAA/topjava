package ru.javawebinar.topjava.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

/**
 * MrArtemAA
 * 05.06.2017
 */
@Component
public class UserFormValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo userTo = (UserTo) target;

        try {
            User checkUser = userService.getByEmail(userTo.getEmail());
            if (userTo.isNew() || !checkUser.getId().equals(userTo.getId())) {
                errors.rejectValue("email", null,"User with this email already present in application");
            }
        } catch (NotFoundException ignored) {

        }
    }
}
