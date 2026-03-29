package ru.javaops.cloudjava.menuservice.storage.repository.updater;

import jakarta.persistence.criteria.CriteriaUpdate;
import lombok.AllArgsConstructor;
import ru.javaops.cloudjava.menuservice.dto.UpdateMenuRequest;
import ru.javaops.cloudjava.menuservice.storage.model.MenuItem;

@AllArgsConstructor
public class MenuAttrUpdater<V> {
  public void updateAttr(final CriteriaUpdate<MenuItem> criteria, final UpdateMenuRequest dto) {
    //  TODO  criteria.set(attr, dtoValue);
  }
}
