package ru.kf.KarlFriedrichBackendStub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kf.KarlFriedrichBackendStub.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

}
