package ru.kf.KarlFriedrichBackendMvp.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableService {
    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public Table getTableById(Long id) {
        return tableRepository.findById(id).orElse(null);
    }

    public Table getTableByQR(String qrCode) {
        return tableRepository.findTableByQrCode(qrCode);
    }

}
