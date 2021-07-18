package ru.kf.KarlFriedrichBackendMvp.utils.code_generator;

import ru.kf.KarlFriedrichBackendMvp.utils.UtilService;

import java.util.Random;

@UtilService
public class ConfirmationCodeGeneratorImpl implements ConfirmationCodeGenerator{

    private static final int MIN_CODE = 100000;
    private static final int MAX_CODE = 999999;

    @Override
    public int generateCode() {
        return new Random().nextInt(MAX_CODE - MIN_CODE) + MIN_CODE;
    }

}
