package ru.kf.KarlFriedrichBackendStub.services;

import org.springframework.stereotype.Service;
import ru.kf.KarlFriedrichBackendStub.security.AccessRefreshTokenData;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    Map<String, AccessRefreshTokenData> accessDataMap;
    Map<String, AccessRefreshTokenData> refreshDataMap;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private static final int TOKEN_LENGTH = 24;

    private final static int ACCESS_EXPIRATION_TIME_IN_MINUTES = 30;
    private final static int REFRESH_EXPIRATION_TIME_IN_DAYS = 180;


    public TokenService() {
        accessDataMap = new ConcurrentHashMap<>();
        refreshDataMap = new ConcurrentHashMap<>();
    }

    public Long getIdByAccessToken(String accessToken) {
        AccessRefreshTokenData data = accessDataMap.get(accessToken);
        if (data != null) {
            return data.getUserId();
        }
        return null;
    }

    public AccessRefreshTokenData startTokensForUser(Long userId) {
        AccessRefreshTokenData accessRefreshTokenData = new AccessRefreshTokenData(
                generateNewToken(),
                generateNewToken(),
                LocalDateTime.now().plusMinutes(ACCESS_EXPIRATION_TIME_IN_MINUTES),
                LocalDateTime.now().plusDays(REFRESH_EXPIRATION_TIME_IN_DAYS),
                userId
        );

        //TODO: Make atomic using locks or use special cache library (the second solution is better)
        accessDataMap.put(accessRefreshTokenData.getAccessToken(), accessRefreshTokenData);
        refreshDataMap.put(accessRefreshTokenData.getRefreshToken(), accessRefreshTokenData);

        return accessRefreshTokenData;
    }

    public Long deleteTokensForUser(String accessToken) {
        AccessRefreshTokenData accessRefreshTokenData = accessDataMap.get(accessToken);
        if (accessRefreshTokenData != null) {
            //TODO: Make atomic using locks or use special cache library (the second solution is better)
            accessDataMap.remove(accessRefreshTokenData.getAccessToken());
            refreshDataMap.remove(accessRefreshTokenData.getRefreshToken());
            return accessRefreshTokenData.getUserId();
        }
        return null;
    }

    public AccessRefreshTokenData refresh(String refreshToken) {
        AccessRefreshTokenData accessRefreshTokenData = refreshDataMap.get(refreshToken);
        if (accessRefreshTokenData != null) {
            //TODO: Don't forget about concurrency and, possibly, atomicity
            deleteTokensForUser(accessRefreshTokenData.getAccessToken());
            return startTokensForUser(accessRefreshTokenData.getUserId());
        }
        return null;
    }

    private static String generateNewToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

}
