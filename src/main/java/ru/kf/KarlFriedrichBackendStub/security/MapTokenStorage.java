package ru.kf.KarlFriedrichBackendStub.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTokenStorage implements TokenStorage{
    private final Map<String, Long> accessTokenToIdMap;

    public MapTokenStorage() {
        this.accessTokenToIdMap = new ConcurrentHashMap<>();
    }

    @Override
    public Long getIdByAccessToken(String accessToken) {
        return accessTokenToIdMap.get(accessToken);
    }

    @Override
    public void addMapping(String accessToken, Long id) {
        accessTokenToIdMap.put(accessToken, id);
    }

    public Long deleteMapping(String accessToken) {
        return accessTokenToIdMap.remove(accessToken);
    }

}
