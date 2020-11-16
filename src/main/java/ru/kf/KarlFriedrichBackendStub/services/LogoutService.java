package ru.kf.KarlFriedrichBackendStub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kf.KarlFriedrichBackendStub.security.TokenStorage;

@Service
public class LogoutService {
    private final TokenStorage tokenStorage;

    @Autowired
    public LogoutService(TokenStorage tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    public boolean logout(String accessToken) {
        return (tokenStorage.deleteMapping(accessToken) != null);
    }

}
