package ru.kf.KarlFriedrichBackendStub.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kf.KarlFriedrichBackendStub.security.AccessRefreshTokenData;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessRefreshTokenDto {
    private String accessToken;
    private LocalDateTime accessExpirationTime;
    private String refreshToken;
    private LocalDateTime refreshExpirationTime;

    public static AccessRefreshTokenDto createFromAccessRefreshTokenData(AccessRefreshTokenData accessRefreshTokenData) {
        return new AccessRefreshTokenDto(
                accessRefreshTokenData.getAccessToken(),
                accessRefreshTokenData.getAccessExpirationTime(),
                accessRefreshTokenData.getRefreshToken(),
                accessRefreshTokenData.getRefreshExpirationTime()
        );
    }

}
