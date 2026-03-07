package ru.javaops.cloudjava.menuservice.storage.model;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_items")
public class MenuItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private Category category;

  @Column(name = "time_to_cook", nullable = false)
  private long timeToCook;

  @Column(name = "weight", nullable = false)
  private double weight;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @Type(JsonBinaryType.class)
  @Column(name = "ingredient_collection", columnDefinition = "jsonb")
  private IngredientCollection ingredientCollection;

  @CreationTimestamp
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Override
  public final boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy
        ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    MenuItem menuItem = (MenuItem) o;
    return getId() != null && Objects.equals(getId(), menuItem.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
