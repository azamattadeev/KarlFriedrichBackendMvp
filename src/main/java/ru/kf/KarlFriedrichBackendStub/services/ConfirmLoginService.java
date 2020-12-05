package ru.kf.KarlFriedrichBackendStub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kf.KarlFriedrichBackendStub.entities.ConfirmationData;
import ru.kf.KarlFriedrichBackendStub.entities.User;
import ru.kf.KarlFriedrichBackendStub.repositories.ConfirmationDataRepository;
import ru.kf.KarlFriedrichBackendStub.repositories.UserRepository;
import ru.kf.KarlFriedrichBackendStub.security.AccessRefreshTokenData;

import java.time.LocalDateTime;

@Service
public class ConfirmLoginService {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final ConfirmationDataRepository confirmationDataRepository;

    @Autowired
    public ConfirmLoginService(TokenService tokenService, UserRepository userRepository, ConfirmationDataRepository confirmationDataRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.confirmationDataRepository = confirmationDataRepository;
    }

    public AccessRefreshTokenData confirm(String email, int confirmationCode){
        User user = userRepository.findUserByEmail(email);
        ConfirmationData confirmationData = confirmationDataRepository.findById(email).orElse(null);

        if (user == null || confirmationData == null) throw new IllegalArgumentException("Fail");

        if (isItExpired(confirmationData)) {
            processExpired(user, confirmationData);
            throw new IllegalArgumentException("Fail");
        }

        if (confirmationCode == confirmationData.getCode()) {
            user.setConfirmed(true);
            userRepository.save(user);
            confirmationDataRepository.deleteById(confirmationData.getEmail());

            return tokenService.startTokensForUser(user.getId());
        } else {
            processWrongCode(user, confirmationData);
            throw new IllegalArgumentException("Fail");
        }
    }

    private void processExpired(User user, ConfirmationData confirmationData) {
        // TODO: DB interactions below must be atomic transaction
        confirmationDataRepository.deleteById(confirmationData.getEmail());
        if (!user.isConfirmed()) userRepository.deleteById(user.getId());
    }

    private void processWrongCode(User user, ConfirmationData confirmationData) {
        // TODO: DB interactions below must be atomic transaction
        confirmationData.setAttemptsLeft(confirmationData.getAttemptsLeft() - 1);
        if (confirmationData.getAttemptsLeft() <= 0) processExpired(user, confirmationData);
    }

    private static boolean isItExpired(ConfirmationData confirmationData) {
        return confirmationData.getExpirationTime().isBefore(LocalDateTime.now());
    }

}
