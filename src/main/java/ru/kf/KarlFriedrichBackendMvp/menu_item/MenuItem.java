package ru.kf.KarlFriedrichBackendMvp.menu_item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kf.KarlFriedrichBackendMvp.menu_item_category.MenuItemCategory;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CATEGORY_ID")
    private MenuItemCategory category;

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
