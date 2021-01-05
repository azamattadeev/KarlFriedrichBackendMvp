package ru.kf.KarlFriedrichBackendStub.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "A_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Table {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private int tableNumber;

    @Column(nullable = false, unique = true)
    private String qrCode;

}
