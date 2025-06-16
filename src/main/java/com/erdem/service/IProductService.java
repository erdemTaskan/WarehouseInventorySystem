package com.erdem.service;

import com.erdem.model.Product;

import java.util.List;

public interface IProductService {
    Product createProduct(Product product);

    Product getProductById(Long id);

    List<Product>gelAllProduct();

    void deleteProduct(Long id);
}
