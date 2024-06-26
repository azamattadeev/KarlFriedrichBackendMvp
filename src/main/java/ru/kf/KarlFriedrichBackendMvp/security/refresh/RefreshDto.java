package ru.kf.KarlFriedrichBackendMvp.security.refresh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshDto {
    @NotNull
    private String refreshToken;
}
