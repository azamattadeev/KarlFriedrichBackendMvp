package ru.kf.KarlFriedrichBackendStub.controllers.cheaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kf.KarlFriedrichBackendStub.entities.ConfirmationData;
import ru.kf.KarlFriedrichBackendStub.repositories.ConfirmationDataRepository;

import java.util.List;

@RestController
@RequestMapping("/cheaters/confirmation-data/")
public class ConfirmationDataCheaterController {
    private final ConfirmationDataRepository confirmationDataRepository;

    @Autowired
    public ConfirmationDataCheaterController(ConfirmationDataRepository confirmationDataRepository) {
        this.confirmationDataRepository = confirmationDataRepository;
    }

    @GetMapping
    public ResponseEntity<List<ConfirmationData>> getAllConfirmationData() {
        List<ConfirmationData> dataList = confirmationDataRepository.findAll();
        return ResponseEntity.ok(dataList);
    }

    @GetMapping("{email}")
    public ResponseEntity<ConfirmationData> getConfirmationData(@PathVariable String email) {
        ConfirmationData confirmationData = confirmationDataRepository.findById(email).orElse(null);
        return (confirmationData != null)
                ? ResponseEntity.ok(confirmationData)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
