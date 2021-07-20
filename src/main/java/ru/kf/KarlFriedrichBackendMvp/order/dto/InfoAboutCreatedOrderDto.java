package ru.kf.KarlFriedrichBackendMvp.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoAboutCreatedOrderDto {
    private Long id;
    private int price;
}
