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
    private final Map<String, AccessRefreshTokenData> accessDataMap;
    private final Map<String, AccessRefreshTokenData> refreshDataMap;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private static final int TOKEN_LENGTH = 24;

    private final static int ACCESS_EXPIRATION_TIME_IN_MINUTES = 30;
    private final static int REFRESH_EXPIRATION_TIME_IN_DAYS = 60;


    public TokenService() {
        accessDataMap = new ConcurrentHashMap<>();
        refreshDataMap = new ConcurrentHashMap<>();
    }

    public Long getIdByAccessToken(String accessToken) {
        AccessRefreshTokenData data = accessDataMap.get(accessToken);
        if (data != null) {
            if (LocalDateTime.now().isAfter(data.getAccessExpirationTime())) {
                accessDataMap.remove(accessToken);
                return null;
            }
            return data.getUserId();
        }
        return null;
    }

    public AccessRefreshTokenData startTokensForUser(Long userId) {
        for (AccessRefreshTokenData accessRefreshTokenData : refreshDataMap.values()) {
            if (userId.equals(accessRefreshTokenData.getUserId())) deleteTokensForUser(accessRefreshTokenData.getAccessToken());
        }
        String accessToken = generateNewToken();
        String refreshToken = generateNewToken();
        boolean tokensUnique;
        do {
            tokensUnique = true;
            if (accessDataMap.containsKey(accessToken)) {
                accessToken = generateNewToken();
                tokensUnique = false;
            }
            if (refreshDataMap.containsKey(refreshToken)) {
                refreshToken = generateNewToken();
                tokensUnique = false;
            }
        } while (!tokensUnique);

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
            if (LocalDateTime.now().isAfter(accessRefreshTokenData.getRefreshExpirationTime())) {
                refreshDataMap.remove(refreshToken);
                accessDataMap.remove(accessRefreshTokenData.getAccessToken());
                return null;
            }
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
