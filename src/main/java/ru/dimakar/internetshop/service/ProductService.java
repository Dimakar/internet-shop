package ru.dimakar.internetshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dimakar.internetshop.dto.AddNewProductRequest;
import ru.dimakar.internetshop.dto.ProductDto;
import ru.dimakar.internetshop.ex.BadRequestException;
import ru.dimakar.internetshop.model.ProductModel;
import ru.dimakar.internetshop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    ModelMapper mapper = new ModelMapper();

    @Transactional
    public ProductDto addNewProduct(AddNewProductRequest addNewProductRequest) {
        ProductModel productModel = productRepository.findProductModelByName(addNewProductRequest.getName());
        if (productModel != null) {
            throw new BadRequestException("Product with name '" + addNewProductRequest.getName() + "' already exists");
        }
        productModel = mapper.map(addNewProductRequest, ProductModel.class);
        return mapper.map(productRepository.save(productModel), ProductDto.class);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream().map(p -> mapper.map(p, ProductDto.class))
                .collect(Collectors.toList());
    }
}
