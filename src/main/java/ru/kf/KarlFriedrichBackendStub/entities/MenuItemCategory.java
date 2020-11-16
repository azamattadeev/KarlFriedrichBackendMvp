package ru.kf.KarlFriedrichBackendStub.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MenuItemCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
