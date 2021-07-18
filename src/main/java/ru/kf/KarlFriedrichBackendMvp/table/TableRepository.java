package ru.kf.KarlFriedrichBackendMvp.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    Table findTableByQrCode(String qrCode);

    Table findTableByTableNumber(int tableNumber);

}
