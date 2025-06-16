package com.erdem.controller;


import com.erdem.model.StockOperation;
import com.erdem.service.IStockOperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class StockOperationController {

    private final IStockOperationService stockOperationService;

    public StockOperationController(IStockOperationService stockOperationService) {
        this.stockOperationService = stockOperationService;
    }
    @PostMapping("/{productId}/stock")
    public ResponseEntity<StockOperation> createOperation(@PathVariable Long productId,
                                                          @RequestBody StockOperation operation){
        return ResponseEntity.ok(stockOperationService.createOperation(productId, operation));
    }
}
