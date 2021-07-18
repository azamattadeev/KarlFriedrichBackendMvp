package ru.kf.KarlFriedrichBackendMvp.security.confirmation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationDataRepository extends JpaRepository<ConfirmationData, String> {
}
