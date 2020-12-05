package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.dto.incoming.LoginDto;
import ru.kf.KarlFriedrichBackendStub.services.SignUpAndLoginService;

@RestController
public class LoginController {
    private final SignUpAndLoginService signUpAndLoginService;

    @Autowired
    public LoginController(SignUpAndLoginService signUpAndLoginService) {
        this.signUpAndLoginService = signUpAndLoginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        try {
            signUpAndLoginService.login(loginDto.getEmail());
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(iae.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
