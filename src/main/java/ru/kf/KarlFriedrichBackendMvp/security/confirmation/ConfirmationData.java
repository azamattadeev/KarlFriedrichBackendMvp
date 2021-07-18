package ru.kf.KarlFriedrichBackendMvp.security.confirmation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationData {
    @Id private String email;
    @Column(nullable = false)
    private int code;
    @Column(nullable = false)
    private LocalDateTime expirationTime;
    @Column(nullable = false)
    private int attemptsLeft;
}
