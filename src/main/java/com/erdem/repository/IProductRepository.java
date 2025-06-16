package com.erdem.repository;

import com.erdem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long id);


    boolean existsByProductCode(String productCode);
}
