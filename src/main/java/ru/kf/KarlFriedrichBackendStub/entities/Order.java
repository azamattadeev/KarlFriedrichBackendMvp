package ru.kf.KarlFriedrichBackendStub.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kf.KarlFriedrichBackendStub.entities.enums.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@javax.persistence.Table(name = "A_ORDER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private OrderStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TABLE_ID")
    private Table table;

    @NotNull
    private int priceInRoubles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ORDER_MENU_ITEMS",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "node_id")})
    private List<MenuItem> itemsList;

    @Column(nullable = false)
    private LocalDateTime orderDateTime;

}
