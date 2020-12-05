package ru.kf.KarlFriedrichBackendStub.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmDto {
    private String email;
    private int confirmationCode;
}
