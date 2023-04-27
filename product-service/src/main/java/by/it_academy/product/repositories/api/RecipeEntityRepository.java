package by.it_academy.product.repositories.api;

import by.it_academy.product.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeEntityRepository extends JpaRepository<RecipeEntity, UUID> {
    boolean existsByTitle(String title);
}
