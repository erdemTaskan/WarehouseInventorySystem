package com.erdem.service.impl;

import com.erdem.exception.ProductNotFoundException;
import com.erdem.exception.StockNotEnoughException;
import com.erdem.model.Product;
import com.erdem.model.StockOperation;
import com.erdem.repository.IProductRepository;
import com.erdem.repository.IStockOperationRepository;
import com.erdem.service.IStockOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional
public class StockOperationServiceImpl implements IStockOperationService {

    private final IProductRepository productRepository;
    private final IStockOperationRepository stockOperationRepository;

    public StockOperationServiceImpl(IProductRepository productRepository, IStockOperationRepository stockOperationRepository) {
        this.productRepository = productRepository;
        this.stockOperationRepository = stockOperationRepository;
    }


    @Override
    public StockOperation createOperation(Long productId, StockOperation operation) {
       Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));

       operation.setProduct(product);
       operation.setOperationDate(LocalDateTime.now());
       int currentQuantity=product.getQuantity();
       int opQuantity=operation.getQuantity();

       if (operation.getType()== StockOperation.TYPE.IN){
           product.setQuantity(currentQuantity+opQuantity);
       } else if (operation.getType()== StockOperation.TYPE.OUT) {
           if (currentQuantity<opQuantity){
               throw new StockNotEnoughException(productId,currentQuantity,opQuantity);
           }
           product.setQuantity(currentQuantity-opQuantity);
       }
       productRepository.save(product);
        return stockOperationRepository.save(operation);
    }
}
