package ru.kf.KarlFriedrichBackendStub.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kf.KarlFriedrichBackendStub.entities.MenuItem;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuItemPreviewDto {
    private Long id;
    private String name;
    private String category;
    private int priceInRoubles;
    private String previewImageUrl;

    public static MenuItemPreviewDto createFromMenuItem(MenuItem menuItem) {
        return new MenuItemPreviewDto(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getCategory().getName(),
                menuItem.getPriceInRoubles(),
                menuItem.getPreviewImageUrl()
        );
    }

    public static List<MenuItemPreviewDto> createListFromMenuItems(List<MenuItem> menuItems) {
        List<MenuItemPreviewDto> dtoList = new ArrayList<>(menuItems.size());
        for (MenuItem item : menuItems) {
            dtoList.add(createFromMenuItem(item));
        }
        return dtoList;
    }

}
