package ru.kf.KarlFriedrichBackendMvp.menu_item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findAllByAvailableIsTrue();

    Set<MenuItem> findAllByIdIn(List<Long> idList);

}
