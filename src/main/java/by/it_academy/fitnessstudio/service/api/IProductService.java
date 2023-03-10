package by.it_academy.fitnessstudio.service.api;

import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.product.Product;
import by.it_academy.fitnessstudio.core.dto.product.ProductCreate;
import by.it_academy.fitnessstudio.entity.ProductEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IProductService {

    void create(@NotNull @Valid ProductCreate productCreate);

    OnePage<Product> getProductsPage(@NotNull Pageable pageable);

    void updateProduct(@NotNull UUID uuid, @NotNull @Past LocalDateTime dtUpdate, @NotNull @Valid ProductCreate productCreate);

    ProductEntity getEntity(@NotNull UUID uuid);
}
