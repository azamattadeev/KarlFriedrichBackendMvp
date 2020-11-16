package ru.kf.KarlFriedrichBackendStub.security;

public interface TokenStorage {

    Long getIdByAccessToken(String accessToken);

    void addMapping(String accessToken, Long id);

    Long deleteMapping(String accessToken);

}
