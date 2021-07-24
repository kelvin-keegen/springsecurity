package com.keegan.springsecurity.controller;

import com.keegan.springsecurity.registration.RegistrationRequest;
import com.keegan.springsecurity.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(path = "/api/user/registration/signup")
    public String AddNewUser(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}
