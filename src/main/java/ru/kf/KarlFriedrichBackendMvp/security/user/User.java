package ru.kf.KarlFriedrichBackendMvp.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kf.KarlFriedrichBackendMvp.order.Order;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "A_USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String language;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private boolean isConfirmed;

}
