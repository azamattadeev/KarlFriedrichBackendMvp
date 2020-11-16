package ru.kf.KarlFriedrichBackendStub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kf.KarlFriedrichBackendStub.entities.ConfirmationData;
import ru.kf.KarlFriedrichBackendStub.entities.User;
import ru.kf.KarlFriedrichBackendStub.repositories.ConfirmationDataRepository;
import ru.kf.KarlFriedrichBackendStub.repositories.UserRepository;
import ru.kf.KarlFriedrichBackendStub.security.TokenStorage;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class ConfirmLoginService {
    private final TokenStorage tokenStorage;
    private final UserRepository userRepository;
    private final ConfirmationDataRepository confirmationDataRepository;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private static final int TOKEN_LENGTH = 24;

    @Autowired
    public ConfirmLoginService(TokenStorage tokenStorage, UserRepository userRepository, ConfirmationDataRepository confirmationDataRepository) {
        this.tokenStorage = tokenStorage;
        this.userRepository = userRepository;
        this.confirmationDataRepository = confirmationDataRepository;
    }

    public String confirm(String email, int confirmationCode){
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

            String accessToken = generateNewToken();
            tokenStorage.addMapping(accessToken, user.getId());
            return accessToken;
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

    public static String generateNewToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

}
