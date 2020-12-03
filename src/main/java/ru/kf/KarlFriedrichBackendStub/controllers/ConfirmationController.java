package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.dto.incoming.ConfirmDto;
import ru.kf.KarlFriedrichBackendStub.services.ConfirmLoginService;

@RestController
public class ConfirmationController {
    private final ConfirmLoginService confirmLoginService;

    @Autowired
    public ConfirmationController(ConfirmLoginService confirmLoginService) {
        this.confirmLoginService = confirmLoginService;
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestBody ConfirmDto confirmDto) {
        try {
            String accessToken = confirmLoginService.confirm(confirmDto.getEmail(), confirmDto.getConfirmationCode());
            return ResponseEntity.ok(accessToken);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        }
    }

}
