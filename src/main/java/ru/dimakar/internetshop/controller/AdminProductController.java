package ru.dimakar.internetshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dimakar.internetshop.dto.AddNewProductRequest;
import ru.dimakar.internetshop.dto.ProductDto;
import ru.dimakar.internetshop.service.ProductService;

import javax.validation.Valid;

@RestController
@Validated
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/api/admin/product")
    public ProductDto addProduct(@Valid @RequestBody AddNewProductRequest addNewProductRequest) {
        return productService.addNewProduct(addNewProductRequest);
    }
}
