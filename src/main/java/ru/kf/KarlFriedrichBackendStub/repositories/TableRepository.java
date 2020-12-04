package ru.kf.KarlFriedrichBackendStub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kf.KarlFriedrichBackendStub.entities.Table;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    Table getTableByQrCode(String qrCode);

}
