package ru.kf.KarlFriedrichBackendStub.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kf.KarlFriedrichBackendStub.entities.enums.PaymentType;

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
    private PaymentType paymentType;
    @NotNull
    @Size(min = 1)
    private List<Long> itemIds;
}
