package com.murilogustavo.luizalabs.exception;

public class ProductAlreadyFavorited extends RuntimeException {
  public ProductAlreadyFavorited(String message) {
        super(message);
    }
}
