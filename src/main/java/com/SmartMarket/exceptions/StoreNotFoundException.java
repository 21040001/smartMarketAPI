package com.SmartMarket.exceptions;

public class StoreNotFoundException extends RuntimeException {

	public StoreNotFoundException(String id) {
		super("Store id'si = " + id + " bo'lgan Store topilmadi.");
	}
	
}
