package com.erdem.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super("Product with ID"+id+" not found");
    }
}
