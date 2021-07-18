package ru.kf.KarlFriedrichBackendMvp.security.logout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kf.KarlFriedrichBackendMvp.security.tokens.TokenService;

@Service
public class LogoutService {
    private final TokenService tokenService;

    @Autowired
    public LogoutService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Long logout(String accessToken) {
        return tokenService.deleteTokensForUser(accessToken);
    }

}
