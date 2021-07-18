package ru.kf.KarlFriedrichBackendMvp.security.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SignUpController {
    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        try {
            signUpService.signUp(signUpDto.getEmail(), signUpDto.getName(), signUpDto.getLanguage());
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(iae.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
