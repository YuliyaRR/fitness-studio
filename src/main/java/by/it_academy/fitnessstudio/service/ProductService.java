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

        int number = all.getNumber();
        int sizePage = all.getSize();
        int totalPages = all.getTotalPages();
        long totalElements = all.getTotalElements();
        boolean first = all.isFirst();
        int numberOfElements = all.getNumberOfElements();
        boolean last = all.isLast();
        return new OnePage<>(number, sizePage, totalPages, totalElements, first, numberOfElements, last, content);//TODO:заменить на билдер
    }

    @Override
    public void updateProduct(UUID uuid, Long dtUpdate, ProductCreate productCreate) {
        InvalidInputServiceMultiException multiException = new InvalidInputServiceMultiException(ErrorCode.STRUCTURED_ERROR);

        if(uuid == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("UUID not entered", "uuid"));
        }

        if(dtUpdate == null) {
            multiException.addSuppressed(new InvalidInputServiceMultiException("No latest update date", "dt_update"));
        }

        if(dtUpdate != null) {
            if (dtUpdate <= 0) {
                multiException.addSuppressed(new InvalidInputServiceMultiException("Field must be a positive number", "dt_update"));
            }
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

        Long timeUpdate = conversionService.convert(productEntity.getDtUpdate(), Long.class);

        if(timeUpdate.equals(dtUpdate)){
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
    public Product get(UUID uuid) {//удалить, если не используется
        if(uuid == null) {
            throw new InvalidInputServiceSingleException("UUID not entered", ErrorCode.ERROR);
        }
        Optional<ProductEntity> productById = repository.findById(uuid);

        if(productById.isEmpty()){
            throw new InvalidInputServiceSingleException("Product with this uuid was not found in the database", ErrorCode.ERROR);
        }

        ProductEntity productEntity = productById.get();
        return Product.ProductBuilder.create()
                .setUuid(productEntity.getUuid())
                .setDtUpdate(conversionService.convert(productEntity.getDtUpdate(), Long.class))
                .build();
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
