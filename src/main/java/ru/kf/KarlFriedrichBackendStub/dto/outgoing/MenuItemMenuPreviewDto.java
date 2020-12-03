package ru.kf.KarlFriedrichBackendStub.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kf.KarlFriedrichBackendStub.entities.MenuItem;

@Data
@AllArgsConstructor
public class MenuItemMenuPreviewDto {
    private Long id;
    private String name;
    private String category;
    private int priceInRoubles;
    private String previewImageUrl;

    public static MenuItemMenuPreviewDto createFromMenuItem(MenuItem menuItem) {
        return new MenuItemMenuPreviewDto(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getCategory().getName(),
                menuItem.getPriceInRoubles(),
                menuItem.getPreviewImageUrl()
        );
    }

}
