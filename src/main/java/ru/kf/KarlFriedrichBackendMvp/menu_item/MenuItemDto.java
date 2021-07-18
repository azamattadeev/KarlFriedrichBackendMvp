package ru.kf.KarlFriedrichBackendMvp.menu_item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDto {
    private Long id;
    private String name;
    private String category;
    private String description;
    private int priceInRoubles;
    private String imageUrl;

    public static MenuItemDto createFromMenuItem(MenuItem menuItem) {
        return new MenuItemDto(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getCategory().getName(),
                menuItem.getDescription(),
                menuItem.getPriceInRoubles(),
                menuItem.getImageUrl()
        );
    }

}
