package by.it_academy.product.repositories.api;

import by.it_academy.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, UUID> {
    boolean existsByTitle(String title);

}
