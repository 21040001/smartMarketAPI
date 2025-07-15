package com.SmartMarket.exceptions;

@SuppressWarnings("serial")
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Mahsulot topilmadi. Store Id yoki mahsulot Idsi xatoli kiritildi.");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
