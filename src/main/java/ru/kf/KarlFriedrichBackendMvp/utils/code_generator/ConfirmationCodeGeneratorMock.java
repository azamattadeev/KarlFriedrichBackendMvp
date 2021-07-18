package ru.kf.KarlFriedrichBackendMvp.utils.code_generator;

import org.springframework.context.annotation.Primary;
import ru.kf.KarlFriedrichBackendMvp.utils.UtilService;

@Primary
@UtilService
public class ConfirmationCodeGeneratorMock implements ConfirmationCodeGenerator {

    @Override
    public int generateCode() {
        return 100500;
    }

}
