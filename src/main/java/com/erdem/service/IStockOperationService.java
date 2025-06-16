package com.erdem.service;

import com.erdem.model.StockOperation;

public interface IStockOperationService {
    StockOperation createOperation(Long productId, StockOperation operation);
}
