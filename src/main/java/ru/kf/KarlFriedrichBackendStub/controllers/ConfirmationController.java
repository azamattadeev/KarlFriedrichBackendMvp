package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.dto.incoming.ConfirmDto;
import ru.kf.KarlFriedrichBackendStub.dto.outgoing.AccessRefreshTokenDto;
import ru.kf.KarlFriedrichBackendStub.security.AccessRefreshTokenData;
import ru.kf.KarlFriedrichBackendStub.services.ConfirmLoginService;

import javax.validation.Valid;

@RestController
public class ConfirmationController {
    private final ConfirmLoginService confirmLoginService;

    @Autowired
    public ConfirmationController(ConfirmLoginService confirmLoginService) {
        this.confirmLoginService = confirmLoginService;
    }

    @PostMapping("/confirm")
    public ResponseEntity<AccessRefreshTokenDto> confirm(@Valid @RequestBody ConfirmDto confirmDto) {
        try {
            AccessRefreshTokenData accessRefreshTokenData = confirmLoginService.confirm(confirmDto.getEmail(), confirmDto.getConfirmationCode());
            return ResponseEntity.ok(AccessRefreshTokenDto.createFromAccessRefreshTokenData(accessRefreshTokenData));
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
