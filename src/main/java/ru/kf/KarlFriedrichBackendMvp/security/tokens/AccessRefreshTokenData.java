package ru.kf.KarlFriedrichBackendMvp.security.tokens;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class AccessRefreshTokenData {
    private final String accessToken;
    private final String refreshToken;
    private final LocalDateTime accessExpirationTime;
    private final LocalDateTime refreshExpirationTime;
    private final Long userId;
}
