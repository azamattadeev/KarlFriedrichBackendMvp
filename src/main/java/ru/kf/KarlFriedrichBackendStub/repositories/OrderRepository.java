package ru.kf.KarlFriedrichBackendStub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kf.KarlFriedrichBackendStub.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
