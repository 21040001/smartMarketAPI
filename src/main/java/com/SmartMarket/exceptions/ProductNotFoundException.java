package com.SmartMarket.exceptions;

@SuppressWarnings("serial")
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String id) {
        super("Mahsulot topilmadi. Store Id yoki mahsulot Idsi xatoli kiritildi."+id);
    }

    
}
