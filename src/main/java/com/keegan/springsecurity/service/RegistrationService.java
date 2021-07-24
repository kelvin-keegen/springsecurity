package com.keegan.springsecurity.service;


import com.keegan.springsecurity.entity.AppUser;
import com.keegan.springsecurity.entity.UserRoles;
import com.keegan.springsecurity.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private static final String InvalidEmail_MSG = "Email entered '%s' is not Valid";

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {

            throw new IllegalStateException(String.format(InvalidEmail_MSG,request.getEmail()));
        }

        return appUserService.SignUp(new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRoles.USER
        ));
    }
}
