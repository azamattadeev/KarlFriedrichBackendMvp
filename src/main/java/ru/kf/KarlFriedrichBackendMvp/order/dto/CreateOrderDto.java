package ru.kf.KarlFriedrichBackendMvp.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
    @NotNull
    private Long tableId;
    @NotNull
    @Size(min = 1)
    private List<Long> itemIds;
}
