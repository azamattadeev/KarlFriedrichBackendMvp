package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.dto.incoming.SignUpDto;
import ru.kf.KarlFriedrichBackendStub.services.SignUpAndLoginService;

@RestController
public class SignUpController {
    private final SignUpAndLoginService signUpAndLoginService;

    @Autowired
    public SignUpController(SignUpAndLoginService signUpAndLoginService) {
        this.signUpAndLoginService = signUpAndLoginService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {
        try {
            signUpAndLoginService.signUp(signUpDto.getEmail(), signUpDto.getName(), signUpDto.getLanguage());
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(iae.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
