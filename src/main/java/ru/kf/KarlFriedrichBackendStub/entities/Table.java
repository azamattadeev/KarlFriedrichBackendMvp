package ru.kf.KarlFriedrichBackendStub.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Table {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private int tableNumber;

    @Column(nullable = false, unique = true)
    private String qrCode;

}
