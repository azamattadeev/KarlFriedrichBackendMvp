package ru.kf.KarlFriedrichBackendStub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kf.KarlFriedrichBackendStub.entities.ConfirmationData;

public interface ConfirmationDataRepository extends JpaRepository<ConfirmationData, String> {
}
