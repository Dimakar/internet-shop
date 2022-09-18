package ru.dimakar.internetshop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dimakar.internetshop.model.ProductModel;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductModel, Long> {
    List<ProductModel> findAll();
    ProductModel findProductModelByName(String name);
}
