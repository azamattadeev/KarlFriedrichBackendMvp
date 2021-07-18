package ru.kf.KarlFriedrichBackendMvp.security.refresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendMvp.security.tokens.AccessRefreshTokenDto;
import ru.kf.KarlFriedrichBackendMvp.security.tokens.AccessRefreshTokenData;
import ru.kf.KarlFriedrichBackendMvp.security.tokens.TokenService;

import javax.validation.Valid;

@RestController
public class RefreshController {
    private final TokenService tokenService;

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
