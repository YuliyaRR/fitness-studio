package by.it_academy.product.service;

import by.it_academy.product.audit.AspectAudit;
import by.it_academy.product.audit.AuditAction;
import by.it_academy.product.audit.EssenceType;
import by.it_academy.product.core.dto.OnePage;
import by.it_academy.product.core.dto.error.ErrorCode;
import by.it_academy.product.core.dto.product.Product;
import by.it_academy.product.core.dto.product.ProductCreate;
import by.it_academy.product.core.exception.ConversionTimeException;
import by.it_academy.product.core.exception.InvalidInputServiceSingleException;
import by.it_academy.product.entity.ProductEntity;
import by.it_academy.product.repositories.api.ProductEntityRepository;
import by.it_academy.product.service.api.IProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Validated
public class ProductService implements IProductService {
    private final ProductEntityRepository repository;
    private final ConversionService conversionService;

    public ProductService(ProductEntityRepository repository,
                          ConversionService conversionService) {
        this.repository = repository;
        this.conversionService = conversionService;
    }

    @Override
    @AspectAudit(action = AuditAction.CREATE_PRODUCT, type = EssenceType.PRODUCT)
    public UUID create(@NotNull @Valid ProductCreate productCreate) {
        checkUniqueTitle(productCreate);

        if(!conversionService.canConvert(ProductCreate.class, ProductEntity.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        ProductEntity productEntity = conversionService.convert(productCreate, ProductEntity.class);
        repository.save(productEntity);
        return productEntity.getUuid();
    }

    @Override
    public OnePage<Product> getProductsPage(@NotNull Pageable pageable) {
        Page<ProductEntity> all = repository.findAll(pageable);

        if(!conversionService.canConvert(ProductEntity.class, Product.class)) {
            throw new ConversionTimeException("Unable to convert", ErrorCode.ERROR);
        }

        List<Product> content = all.get().map(entity -> conversionService.convert(entity, Product.class)).toList();

        return OnePage.OnePageBuilder.create(content)
                .setNumber(all.getNumber())
                .setSize(all.getSize())
                .setTotalPages(all.getTotalPages())
                .setTotalElements(all.getTotalElements())
                .setFirst(all.isFirst())
                .setNumberOfElements(all.getNumberOfElements())
                .setLast(all.isLast())
                .build();
    }

    @Override
    @AspectAudit(action = AuditAction.UPDATED_PRODUCT, type = EssenceType.PRODUCT)
    public UUID updateProduct(@NotNull UUID uuid, @NotNull @Past LocalDateTime dtUpdate, @NotNull @Valid ProductCreate productCreate) {
        String productCreateTitle = productCreate.getTitle();

        ProductEntity productEntity = repository.findById(uuid)
                .orElseThrow(() -> new InvalidInputServiceSingleException("Product with this uuid doesn't exist", ErrorCode.ERROR));


        if(productEntity.getDtUpdate().equals(dtUpdate)){
           if(!productEntity.getTitle().equals(productCreateTitle)) {
               checkUniqueTitle(productCreate);
           }
            productEntity.setTitle(productCreate.getTitle());
            productEntity.setWeight(productCreate.getWeight());
            productEntity.setCalories(productCreate.getCalories());
            productEntity.setProteins(productCreate.getProteins());
            productEntity.setFats(productCreate.getFats());
            productEntity.setCarbohydrates(productCreate.getCarbohydrates());
            repository.save(productEntity);
        } else {
            throw new InvalidInputServiceSingleException("Product with this version doesn't exist", ErrorCode.ERROR);
        }
        return productEntity.getUuid();
    }

    @Override
    public ProductEntity getEntity(@NotNull UUID uuid) {
        return repository.findById(uuid).
                orElseThrow(() -> new InvalidInputServiceSingleException("Product with this uuid doesn't exist", ErrorCode.ERROR));
    }

    private void checkUniqueTitle(ProductCreate productCreate) {
        if(repository.existsByTitle(productCreate.getTitle())) {
            throw new InvalidInputServiceSingleException("Not a unique product name", ErrorCode.ERROR);
        }
    }
}
