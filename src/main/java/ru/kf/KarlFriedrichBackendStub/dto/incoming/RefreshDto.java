package ru.kf.KarlFriedrichBackendStub.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshDto {
    private String refreshToken;
}