package ru.javaops.cloudjava.menuservice.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.cloudjava.menuservice.storage.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>, CustomMenuItemRepository {
}
