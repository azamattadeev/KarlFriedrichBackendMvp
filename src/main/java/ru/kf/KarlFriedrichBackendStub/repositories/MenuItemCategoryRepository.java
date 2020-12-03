package ru.kf.KarlFriedrichBackendStub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kf.KarlFriedrichBackendStub.entities.MenuItemCategory;

@Repository
public interface MenuItemCategoryRepository extends JpaRepository<MenuItemCategory, Long> {
}
