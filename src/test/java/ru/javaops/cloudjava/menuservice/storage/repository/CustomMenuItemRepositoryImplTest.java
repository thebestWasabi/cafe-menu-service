package ru.javaops.cloudjava.menuservice.storage.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.cloudjava.menuservice.storage.model.MenuItem_;
import ru.javaops.cloudjava.menuservice.testutils.TestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@Transactional(propagation = Propagation.NEVER)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlGroup({
    @Sql(scripts = "classpath:insert-menu.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(scripts = "classpath:clear-menus.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class CustomMenuItemRepositoryImplTest {
  @Autowired
  private MenuItemRepository menuItemRepository;

  @Autowired
  private EntityManager em;

  @Test
  void updateMenu_updatedMenu_whenAllFieldsIsNotNull() {
    var dto = TestData.updateMenuFullRequest();
    var id = getIdByName("Cappuccino");
    int updateCount = menuItemRepository.updateMenu(id, dto);
    var updatedMenuItem = menuItemRepository.findById(id).
        orElseThrow(() -> new AssertionError("MenuItem with id %d not found".formatted(id)));

    assertAll(
        () -> assertThat(updateCount).isEqualTo(1),
        () -> assertThat(updatedMenuItem.getId()).isEqualTo(id),
        () -> assertThat(updatedMenuItem.getName()).isEqualTo(dto.getName()),
        () -> assertThat(updatedMenuItem.getDescription()).isEqualTo(dto.getDescription()),
        () -> assertThat(updatedMenuItem.getPrice()).isEqualTo(dto.getPrice()),
        () -> assertThat(updatedMenuItem.getTimeToCook()).isEqualTo(dto.getTimeToCook()),
        () -> assertThat(updatedMenuItem.getImageUrl()).isEqualTo(dto.getImageUrl())
    );
  }

  @Test
  void getMenusFor() {
  }

  private long getIdByName(final String name) {
    return em.createQuery("select m.id from MenuItem m where m.name = :name", Long.class)
        .setParameter(MenuItem_.NAME, name)
        .getSingleResult();
  }
}
