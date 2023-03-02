package by.it_academy.fitnessstudio.service.api;

import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.product.Product;
import by.it_academy.fitnessstudio.core.dto.product.ProductCreate;
import by.it_academy.fitnessstudio.entity.ProductEntity;

import java.util.UUID;

public interface IProductService {

    void create(ProductCreate productCreate);

    OnePage<Product> getProductsPage(Integer page, Integer size);

    void updateProduct(UUID uuid, Long dtUpdate, ProductCreate productCreate);

    Product get(UUID uuid);

    ProductEntity getEntity(UUID uuid);
}
