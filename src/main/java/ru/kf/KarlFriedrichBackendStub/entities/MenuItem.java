package ru.kf.KarlFriedrichBackendStub.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private MenuItemCategory category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int priceInRoubles;

    @Column(nullable = false)
    private boolean accessibility;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String previewImageUrl;
}
