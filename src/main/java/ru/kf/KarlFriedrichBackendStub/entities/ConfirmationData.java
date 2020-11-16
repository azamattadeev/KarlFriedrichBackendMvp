package ru.kf.KarlFriedrichBackendStub.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationData {
    @Id private String email;
    private int code;
    private LocalDateTime expirationTime;
    private int attemptsLeft;
}
