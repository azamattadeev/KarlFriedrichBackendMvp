package ru.kf.KarlFriedrichBackendStub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.dto.incoming.RefreshDto;
import ru.kf.KarlFriedrichBackendStub.dto.outgoing.AccessRefreshTokenDto;
import ru.kf.KarlFriedrichBackendStub.security.AccessRefreshTokenData;
import ru.kf.KarlFriedrichBackendStub.services.TokenService;

import javax.validation.Valid;

@RestController
public class RefreshController {
    private TokenService tokenService;

    @Autowired
    public RefreshController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessRefreshTokenDto> refresh(@Valid @RequestBody RefreshDto refreshDto) {
        AccessRefreshTokenData accessRefreshTokenData = tokenService.refresh(refreshDto.getRefreshToken());
        if (accessRefreshTokenData != null) {
            return ResponseEntity.ok(AccessRefreshTokenDto.createFromAccessRefreshTokenData(accessRefreshTokenData));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
