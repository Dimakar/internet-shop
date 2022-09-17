package ru.dimakar.internetshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dimakar.internetshop.dto.AddNewProductRequest;
import ru.dimakar.internetshop.dto.ProductDto;
import ru.dimakar.internetshop.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/api/product")
    public ProductDto addProduct(@Valid @RequestBody AddNewProductRequest addNewProductRequest) {
        return productService.addNewProduct(addNewProductRequest);
    }

    @GetMapping("/api/product")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }
}
