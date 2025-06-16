package com.erdem.exception;

public class StockOperationNotFoundException extends RuntimeException {

    public StockOperationNotFoundException(Long id){
        super("Stock operation with ID"+id+" not found");
    }

}
