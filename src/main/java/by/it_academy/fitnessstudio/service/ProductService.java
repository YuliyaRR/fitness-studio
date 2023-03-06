package by.it_academy.fitnessstudio.service;

import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.error.ErrorCode;
import by.it_academy.fitnessstudio.core.dto.product.Product;
import by.it_academy.fitnessstudio.core.dto.product.ProductCreate;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceMultiException;
import by.it_academy.fitnessstudio.core.exception.InvalidInputServiceSingleException;
import by.it_academy.fitnessstudio.entity.ProductEntity;
import by.it_academy.fitnessstudio.repositories.api.ProductEntityRepository;
import by.it_academy.fitnessstudio.service.api.IProductService;
import by.it_academy.fitnessstudio.validator.api.IValidator;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService implements IProductService {
    private final ProductEntityRepository repository;
    private final ConversionService conversionService;
    private final IValidator<ProductCreate> validator;

    public ProductService(ProductEntityRepository repository, ConversionService conversionService, IValidator<ProductCreate> validator) {
        this.repository = repository;
        this.conversionService = conversionService;
        this.validator = validator;
    }

    @Override
    public void create(ProductCreate productCreate) {
        if(productCreate == null) {
            throw new InvalidInputServiceSingleException("Product information not submitted for create", ErrorCode.ERROR);
        }
        validator.validate(productCreate);
        checkUniqueTitle(productCreate);
        ProductEntity productEntity = conversionService.convert(productCreate, ProductEntity.class);
        repository.save(productEntity);
    }

    @Override
    public OnePage<Product> getProductsPage(Integer page, Integer size) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);

        if(page == null || page < 0) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Invalid field value. Field must be 0 or greater", "page"));
        }

        if(size == null || size <= 0) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Invalid field value. Field must be greater than 0", "size"));
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }

        Page<ProductEntity> all = repository.findAll(PageRequest.of(page, size));

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
    public void updateProduct(UUID uuid, LocalDateTime dtUpdate, ProductCreate productCreate) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);

        if(uuid == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("UUID not entered", "uuid"));
        }

        if(dtUpdate == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("No latest update date", "dt_update"));
        }

        if(productCreate == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("Product information not submitted for update", "product"));
        }

        if(multiException.getSuppressed().length != 0) {
            throw multiException;
        }

        validator.validate(productCreate);

        String productCreateTitle = productCreate.getTitle();

        Optional<ProductEntity> productById = repository.findById(uuid);

        if(productById.isEmpty()){
            throw new InvalidInputServiceSingleException("Product with this uuid doesn't exist", ErrorCode.ERROR);
        }

        ProductEntity productEntity = productById.get();

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
    }

    @Override
    public ProductEntity getEntity(UUID uuid) {
        if(uuid == null) {
            throw new InvalidInputServiceSingleException("UUID not entered", ErrorCode.ERROR);
        }

        Optional<ProductEntity> productById = repository.findById(uuid);

        if(productById.isEmpty()){
            throw new InvalidInputServiceSingleException("Product with this uuid doesn't exist", ErrorCode.ERROR);
        }

        return productById.get();
    }

    private void checkUniqueTitle(ProductCreate productCreate) {
        if(repository.existsByTitle(productCreate.getTitle())) {
            throw new InvalidInputServiceSingleException("Not a unique product name", ErrorCode.ERROR);
        }
    }
}
