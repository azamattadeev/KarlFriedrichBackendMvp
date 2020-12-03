package ru.kf.KarlFriedrichBackendStub.dto.incoming;

import lombok.Data;

@Data
public class ConfirmDto {
    private String email;
    private int confirmationCode;
}
