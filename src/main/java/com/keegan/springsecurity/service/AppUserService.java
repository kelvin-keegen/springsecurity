package com.keegan.springsecurity.service;

import com.keegan.springsecurity.entity.AppUser;
import com.keegan.springsecurity.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String UserNotFound_MSG = "User with email %s Not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return appUserRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException(String.format(UserNotFound_MSG,email)));
    }

    public String SignUp(AppUser appUser) {

       boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

       if (userExists) {
           throw new IllegalStateException("Email is already taken");
       }

       String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
       appUser.setPassword(encodedPassword);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localTime = LocalDateTime.now();

        appUser.setTime(dateTimeFormatter.format(localTime));

       appUserRepository.save(appUser);

        return encodedPassword;
    }
}
