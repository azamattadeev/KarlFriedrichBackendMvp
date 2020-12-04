package ru.kf.KarlFriedrichBackendStub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kf.KarlFriedrichBackendStub.entities.Table;
import ru.kf.KarlFriedrichBackendStub.repositories.TableRepository;

@Service
public class TableService {
    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public Table getTableById(Long id) {
        return tableRepository.getOne(id);
    }

    public Table getTableByQR(String qrCode) {
        return tableRepository.getTableByQrCode(qrCode);
    }

}
