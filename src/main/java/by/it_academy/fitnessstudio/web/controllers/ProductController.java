package by.it_academy.fitnessstudio.web.controllers;

import by.it_academy.fitnessstudio.core.dto.OnePage;
import by.it_academy.fitnessstudio.core.dto.product.Product;
import by.it_academy.fitnessstudio.core.dto.product.ProductCreate;
import by.it_academy.fitnessstudio.service.api.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/product")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody ProductCreate productCreate) {
        productService.create(productCreate);
    }

    @RequestMapping(method = RequestMethod.GET)
    public OnePage<Product> getProductsPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "20") Integer size) {
        return productService.getProductsPage(page, size);
    }

    @RequestMapping(path = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable("uuid") UUID uuid,
                              @PathVariable("dt_update") Long dtUpdate,
                              @RequestBody ProductCreate productCreate) {
        productService.updateProduct(uuid, dtUpdate, productCreate);
    }


}
