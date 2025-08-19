package com.SmartMarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="payTypeTable")
public class TypeOfPay {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name = "whichCashier")
	private String whichCashier;
	
	@Column(name = "storeId")
	private int storeId;
	
	@Column(name = "typeOfPay")
	private String typeOfPay;
	
	@Column(name = "amountOfCash")
	private double amountOfCash;
	
	@Column(name = "totalAmount")
	private double totalAmmaount;
	
	@Column(name = "isEnable")
	private boolean isEnable;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWhichCashier() {
		return whichCashier;
	}

	public void setWhichCashier(String whichCashier) {
		this.whichCashier = whichCashier;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
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
