package ru.kf.KarlFriedrichBackendStub.dto;

import lombok.Data;

@Data
public class ConfirmDto {
    private String email;
    private int confirmationCode;
}
