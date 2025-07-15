package com.SmartMarket.exceptions;

@SuppressWarnings("serial")
public class SaleNotFoundException extends RuntimeException {

	public SaleNotFoundException(String id) {
		super("Kiritilgan idga ko'ra, bu storega tegishli bir savdo topilmadi. kiritgan idingiz = " + id);
	}

	
}
