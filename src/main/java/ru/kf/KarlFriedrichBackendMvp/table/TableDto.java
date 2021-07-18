package ru.kf.KarlFriedrichBackendMvp.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    private Long id;
    private int tableNumber;
    private String qrCode;

    public static TableDto createFromTable(Table table) {
        return new TableDto(
                table.getId(),
                table.getTableNumber(),
                table.getQrCode()
        );
    }

}
