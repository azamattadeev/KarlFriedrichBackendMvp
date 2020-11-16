package ru.kf.KarlFriedrichBackendStub.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Table table;

    @Column(nullable = false)
    private LocalDateTime orderDateTime;

}
