package ru.kf.KarlFriedrichBackendMvp.security.confirmation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationDto {
    @NotBlank
    @Email
    private String email;
    @NotNull
    private Integer confirmationCode;
}
