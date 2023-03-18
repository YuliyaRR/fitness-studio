package by.it_academy.product.web.controllers;

import by.it_academy.product.core.dto.OnePage;
import by.it_academy.product.core.dto.product.Product;
import by.it_academy.product.core.dto.product.ProductCreate;
import by.it_academy.product.service.api.IProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
@RestController
@RequestMapping(path = "/product")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@Valid @RequestBody ProductCreate productCreate) {
        productService.create(productCreate);
    }

    @RequestMapping(method = RequestMethod.GET)
    public OnePage<Product> getProductsPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getProductsPage(pageable);
    }

    @RequestMapping(path = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable("uuid") UUID uuid,
                              @PathVariable("dt_update") @Past LocalDateTime dtUpdate,
                              @Valid @RequestBody ProductCreate productCreate) {
        productService.updateProduct(uuid, dtUpdate, productCreate);
    }


}
