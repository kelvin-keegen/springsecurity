package com.keegan.springsecurity.service;

import com.keegan.springsecurity.entity.ConfirmationToken;
import com.keegan.springsecurity.repository.ConfirmationTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepo confirmationTokenRepo;

    public void SavingToken(ConfirmationToken confirmationToken) {

        confirmationTokenRepo.save(confirmationToken);

    }

    public Optional<ConfirmationToken> getToken(String token) {

        return confirmationTokenRepo.findByToken(token);
    }

    public int UpdateConfirmTime(String token) {

        return confirmationTokenRepo.UpdateConfirmedTime(token, LocalDateTime.now());
    }
}
