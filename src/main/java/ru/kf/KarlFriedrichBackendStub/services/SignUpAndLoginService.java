package ru.kf.KarlFriedrichBackendStub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kf.KarlFriedrichBackendStub.entities.ConfirmationData;
import ru.kf.KarlFriedrichBackendStub.entities.User;
import ru.kf.KarlFriedrichBackendStub.repositories.ConfirmationDataRepository;
import ru.kf.KarlFriedrichBackendStub.repositories.UserRepository;

import java.time.LocalDateTime;

@Service
public class SignUpAndLoginService {
    private final UserRepository userRepository;
    private final ConfirmationDataRepository confirmationDataRepository;

//    private static final int MIN_CODE = 100000;
//    private static final int MAX_CODE = 999999;

    private static final int HOW_MANY_MINUTES_CODE_IS_ACTUAL = 15;

    private static final int MAX_CONFIRMATION_ATTEMPTS = 3;

    @Autowired
    public SignUpAndLoginService(UserRepository userRepository, ConfirmationDataRepository confirmationDataRepository) {
        this.userRepository = userRepository;
        this.confirmationDataRepository = confirmationDataRepository;
    }

    public void signUp(String email, String name, String language) {
        if (userRepository.findUserByEmail(email) != null) throw new IllegalArgumentException("This email is busy");

        User user = new User(null, email, name, "ROLE_USER", language, null, false);
        userRepository.save(user);

        ConfirmationData confirmationData = new ConfirmationData(
                email,
                generateCode(),
                LocalDateTime.now().plusMinutes(HOW_MANY_MINUTES_CODE_IS_ACTUAL),
                MAX_CONFIRMATION_ATTEMPTS
        );
        confirmationDataRepository.save(confirmationData);
    }

    public void login(String email) {
        if (userRepository.findUserByEmail(email) == null) throw new IllegalArgumentException("User with this email is not found ");

        ConfirmationData confirmationData = new ConfirmationData(
                email,
                generateCode(),
                LocalDateTime.now().plusMinutes(HOW_MANY_MINUTES_CODE_IS_ACTUAL),
                MAX_CONFIRMATION_ATTEMPTS
        );
        confirmationDataRepository.save(confirmationData);
    }

    private static int generateCode() {
//        return new Random().nextInt(MAX_CODE - MIN_CODE) + MIN_CODE;
        return 100500;
    }

}
