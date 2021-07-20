package ru.kf.KarlFriedrichBackendMvp.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.kf.KarlFriedrichBackendMvp.table.Table;
import ru.kf.KarlFriedrichBackendMvp.table.TableRepository;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class TableRepositoryTest {

    @Autowired
    private TableRepository tableRepository;

    @Test
    void saveTest() {
        int tableNumber = 45;
        String qrCode = "45";

        Table table = new Table(null, tableNumber, qrCode, 1000321);
        table = tableRepository.save(table);

        assertNotNull(table.getId());
        assertEquals(tableNumber, table.getTableNumber());

        table = tableRepository.findById(table.getId()).orElse(null);

        assertNotNull(table);
        assertEquals(qrCode, table.getQrCode());

        tableRepository.deleteById(table.getId());
        table = tableRepository.findById(table.getId()).orElse(null);
        assertNull(table);
    }

    @Test
    void findByNumberAndQRTest() {
        Table table1 = new Table(null, 11, "11", 1100011);
        Table table2 = new Table(null, 22, "22", 1100022);
        Table table3 = new Table(null, 33, "33", 1100033);
        Table table4 = new Table(null, 44, "44", 1100044);

        table1 = tableRepository.save(table1);
        table2 = tableRepository.save(table2);
        table3 = tableRepository.save(table3);
        table4 = tableRepository.save(table4);

        assertEquals(table1.getId(), tableRepository.findTableByTableNumber(11).getId());

        assertEquals(table3.getId(), tableRepository.findTableByTableNumber(33).getId());

        assertEquals(table4, tableRepository.findTableByTableNumber(44));

        assertEquals(tableRepository.findTableByTableNumber(table2.getTableNumber()), tableRepository.findTableByQrCode(table2.getQrCode()));

        tableRepository.delete(table1);
        tableRepository.delete(table2);
        tableRepository.delete(new Table(table3.getId(), -1, null, 10003004));
        tableRepository.deleteById(table4.getId());

        assertNull(tableRepository.findById(table1.getId()).orElse(null));
        assertNull(tableRepository.findById(table2.getId()).orElse(null));
        assertNull(tableRepository.findById(table3.getId()).orElse(null));
        assertNull(tableRepository.findById(table4.getId()).orElse(null));
    }

}
