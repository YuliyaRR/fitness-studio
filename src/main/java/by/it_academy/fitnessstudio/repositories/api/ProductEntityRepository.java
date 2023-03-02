package by.it_academy.fitnessstudio.repositories.api;

import by.it_academy.fitnessstudio.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductEntityRepository extends CrudRepository<ProductEntity, UUID>, PagingAndSortingRepository<ProductEntity, UUID> {
    boolean existsByTitle(String title);

}
