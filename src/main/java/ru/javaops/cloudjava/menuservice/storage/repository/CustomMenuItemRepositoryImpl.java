package ru.javaops.cloudjava.menuservice.storage.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.cloudjava.menuservice.dto.SortBy;
import ru.javaops.cloudjava.menuservice.dto.UpdateMenuRequest;
import ru.javaops.cloudjava.menuservice.storage.model.Category;
import ru.javaops.cloudjava.menuservice.storage.model.MenuItem;
import ru.javaops.cloudjava.menuservice.storage.model.MenuItem_;
import ru.javaops.cloudjava.menuservice.storage.repository.updater.MenuAttrUpdater;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomMenuItemRepositoryImpl implements CustomMenuItemRepository {
  private final EntityManager em;
  private final List<MenuAttrUpdater<?>> menuAttrUpdaters;

  @Override
  @Transactional
  public int updateMenu(final long id, final UpdateMenuRequest dto) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaUpdate<MenuItem> criteriaUpdate = cb.createCriteriaUpdate(MenuItem.class);
    Root<MenuItem> root = criteriaUpdate.from(MenuItem.class);
    if (dto.getName() != null) criteriaUpdate.set(root.get(MenuItem_.name), dto.getName());
    if (dto.getDescription() != null) criteriaUpdate.set(root.get(MenuItem_.description), dto.getDescription());
    if (dto.getPrice() != null) criteriaUpdate.set(root.get(MenuItem_.price), dto.getPrice());
    if (dto.getTimeToCook() != null) criteriaUpdate.set(root.get(MenuItem_.timeToCook), dto.getTimeToCook());
    if (dto.getImageUrl() != null) criteriaUpdate.set(root.get(MenuItem_.imageUrl), dto.getImageUrl());
    criteriaUpdate.where(cb.equal(root.get(MenuItem_.id), id));

    return em.createQuery(criteriaUpdate).executeUpdate();
  }

  @Override
  public List<MenuItem> getMenusFor(final Category category, final SortBy sortBy) {
    return List.of(); //TODO
  }
}
