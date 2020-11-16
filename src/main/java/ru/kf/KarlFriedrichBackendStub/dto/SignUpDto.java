package ru.kf.KarlFriedrichBackendStub.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String email;
    private String name;
    private String language; // In two letters. For example: "RU", "EN", "en".
}
