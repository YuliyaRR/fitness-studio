package by.it_academy.product.repositories.api;


import by.it_academy.product.entity.RecipeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeEntityRepository extends CrudRepository<RecipeEntity, UUID>, PagingAndSortingRepository<RecipeEntity, UUID> {
    boolean existsByTitle(String title);
}
