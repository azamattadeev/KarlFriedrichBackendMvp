package ru.kf.KarlFriedrichBackendStub.controllers.cheaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.security.AccessRefreshTokenData;
import ru.kf.KarlFriedrichBackendStub.services.TokenService;

import java.util.List;

@RestController
@RequestMapping("/cheaters/tokens/")
public class TokensCheaterController {
    private final TokenService tokenService;

    @Autowired
    public TokensCheaterController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<List<AccessRefreshTokenData>> getAllTokenData() {
        return ResponseEntity.ok(tokenService.getAllTokensData());
    }

    @GetMapping("{id}")
    public ResponseEntity<AccessRefreshTokenData> getTokenDataByUserId(@PathVariable Long id) {
        for (AccessRefreshTokenData data : tokenService.getAllTokensData()) {
            if (data.getUserId().equals(id)) return ResponseEntity.ok(data);
        }
        return ResponseEntity.notFound().build();
    }

}
