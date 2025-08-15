package com.SmartMarket.dto;

public class TypeOfPayDTO {

	private String whichCashier;
	
	private String typeOfPay;
	
	private double amountOfCash;
	
	private double totalAmmaount;
	
	private boolean isEnable;
	

	public TypeOfPayDTO() {
		super();
	}


	public String getWhichCashier() {
		return whichCashier;
	}

	public void setWhichCashier(String whichCashier) {
		this.whichCashier = whichCashier;
	}

	public String getTypeOfPay() {
		return typeOfPay;
	}

	public void setTypeOfPay(String typeOfPay) {
		this.typeOfPay = typeOfPay;
	}

	public double getAmountOfCash() {
		return amountOfCash;
	}

	public void setAmountOfCash(double amountOfCash) {
		this.amountOfCash = amountOfCash;
	}

	public double getTotalAmmaount() {
		return totalAmmaount;
	}

	public void setTotalAmmaount(double totalAmmaount) {
		this.totalAmmaount = totalAmmaount;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	
	
}
