package ru.kf.KarlFriedrichBackendMvp.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kf.KarlFriedrichBackendMvp.menu_item.MenuItem;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    private Long id;
    private String name;
    private String category;
    private int priceInRoubles;
    private String previewImageUrl;

    public static MenuDto createFromMenuItem(MenuItem menuItem) {
        return new MenuDto(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getCategory().getName(),
                menuItem.getPriceInRoubles(),
                menuItem.getPreviewImageUrl()
        );
    }

    public static List<MenuDto> createListFromMenuItems(List<MenuItem> menuItems) {
        List<MenuDto> dtoList = new ArrayList<>(menuItems.size());
        for (MenuItem item : menuItems) {
            dtoList.add(createFromMenuItem(item));
        }
        return dtoList;
    }

}
