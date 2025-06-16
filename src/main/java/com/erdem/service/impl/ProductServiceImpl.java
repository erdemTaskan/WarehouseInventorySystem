package com.erdem.service.impl;

import com.erdem.exception.ProductAlreadyExistsException;
import com.erdem.exception.ProductNotFoundException;
import com.erdem.jwt.JwtAuthenticationFilter;
import com.erdem.model.Product;
import com.erdem.repository.IProductRepository;
import com.erdem.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    private static final Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    private final IProductRepository productRepository;

    public ProductServiceImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        if (productRepository.existsByProductCode(product.getProductCode())){
            logger.error( "This product already exist");
            throw new ProductAlreadyExistsException(product.getProductCode());
        }
        product.setQuantity(0);
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> gelAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);

    }
}
