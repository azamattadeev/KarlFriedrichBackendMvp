package ru.kf.KarlFriedrichBackendMvp.security.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kf.KarlFriedrichBackendMvp.security.confirmation.ConfirmationData;
import ru.kf.KarlFriedrichBackendMvp.security.user.User;
import ru.kf.KarlFriedrichBackendMvp.security.confirmation.ConfirmationDataRepository;
import ru.kf.KarlFriedrichBackendMvp.security.user.UserRepository;
import ru.kf.KarlFriedrichBackendMvp.utils.code_generator.ConfirmationCodeGenerator;

import java.time.LocalDateTime;

import static ru.kf.KarlFriedrichBackendMvp.security.Constants.CONFIRMATION_CODE_LIFE_TIME_MINUTES;
import static ru.kf.KarlFriedrichBackendMvp.security.Constants.MAX_CONFIRMATION_CODE_ATTEMPTS;

@Service
@Transactional
public class SignUpService {
    private final UserRepository userRepository;
    private final ConfirmationDataRepository confirmationDataRepository;
    private final ConfirmationCodeGenerator codeGenerator;

    @Autowired
    public SignUpService(
            UserRepository userRepository,
            ConfirmationDataRepository confirmationDataRepository,
            ConfirmationCodeGenerator codeGenerator
    ) {
        this.userRepository = userRepository;
        this.confirmationDataRepository = confirmationDataRepository;
        this.codeGenerator = codeGenerator;
    }

    public void signUp(String email, String name, String language) {
        if (userRepository.findUserByEmail(email) != null) throw new IllegalArgumentException("This email is busy");

        User user = new User(null, email, name, "ROLE_USER", language, null, false);
        userRepository.save(user);

        ConfirmationData confirmationData = new ConfirmationData(
                email,
                codeGenerator.generateCode(),
                LocalDateTime.now().plusMinutes(CONFIRMATION_CODE_LIFE_TIME_MINUTES),
                MAX_CONFIRMATION_CODE_ATTEMPTS
        );
        confirmationDataRepository.save(confirmationData);
    }

}
