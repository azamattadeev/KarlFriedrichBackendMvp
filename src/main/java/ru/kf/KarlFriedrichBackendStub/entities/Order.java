package ru.kf.KarlFriedrichBackendStub.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Table table;

    @ManyToMany
    private List<MenuItem> itemsList;

    @Column(nullable = false)
    private LocalDateTime orderDateTime;

}
