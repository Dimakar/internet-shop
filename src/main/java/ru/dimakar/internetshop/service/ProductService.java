package ru.dimakar.internetshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dimakar.internetshop.dto.AddNewProductRequest;
import ru.dimakar.internetshop.dto.ProductDto;
import ru.dimakar.internetshop.model.ProductModel;
import ru.dimakar.internetshop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    ModelMapper mapper = new ModelMapper();

    public ProductDto addNewProduct(AddNewProductRequest addNewProductRequest) {
        // TODO: 17.09.2022 check that name is unique
        ProductModel productModel = mapper.map(addNewProductRequest, ProductModel.class);
        return mapper.map(productRepository.save(productModel), ProductDto.class);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream().map(p -> mapper.map(p, ProductDto.class))
                .collect(Collectors.toList());
    }
}
