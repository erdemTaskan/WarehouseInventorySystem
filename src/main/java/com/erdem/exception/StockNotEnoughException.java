package com.erdem.exception;

public class StockNotEnoughException extends RuntimeException{
    public StockNotEnoughException(Long productId, int available, int request){
        super("Stock is not enough for product ID "+productId+".Available: "+available+" ,Requested: "+request);
    }
}
