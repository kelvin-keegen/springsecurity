package com.keegan.springsecurity.controller;

import com.keegan.springsecurity.registration.RegistrationRequest;
import com.keegan.springsecurity.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(path = "/signup")
    public String AddNewUser(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public String Confirm(@RequestParam("token") String token) {

        return registrationService.ConfirmToken(token);
    }

}
