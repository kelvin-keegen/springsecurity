package com.keegan.springsecurity.service;


import com.keegan.springsecurity.entity.AppUser;
import com.keegan.springsecurity.entity.ConfirmationToken;
import com.keegan.springsecurity.entity.UserRoles;
import com.keegan.springsecurity.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationService {

    private static final String InvalidEmail_MSG = "Email entered '%s' is not Valid";

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private  final ConfirmationTokenService confirmationTokenService;

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

    @Transactional
    public String ConfirmToken(String token) {

       ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
               .orElseThrow(() -> new IllegalStateException("Token not found"));

       if (confirmationToken.getConfirmedAt() != null) {
           throw new IllegalStateException("Email is already confirmed");
       }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        LocalDateTime CurrentTime = LocalDateTime.now();

       if (expiredAt.isBefore(CurrentTime)) {
           throw new IllegalStateException("Token is expired");
       }

       confirmationTokenService.UpdateConfirmTime(token);
       appUserService.EnableAppUser(confirmationToken.getAppUser().getEmail());

        return "Confimed";

    }
}
