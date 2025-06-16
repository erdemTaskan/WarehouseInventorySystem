package com.erdem.exception;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(String productCode){
        super("Product with code"+productCode+" already exists");
    }
}
