package com.SmartMarket.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "storeId")
    private int storeId;

    @NotNull
    @Column(name = "saleDate", updatable = false)
    private LocalDateTime saleDate;

    @NotNull
    @Column(name = "totalPrice")
    private double totalPrice;

    @NotNull
    @NotEmpty(message = "Mahsulot nomi bo'sh bo'lishi mumkin emas")
    @Column(name = "productName")
    private String productName;
    
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Shtrix kod noto'g'ri")
    @Column(name = "barcode")
    private String barcode;
    
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    
    @NotNull
    @Column(name = "isCash")
    private boolean isCash;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public LocalDateTime getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDateTime saleDate) {
		this.saleDate = saleDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public boolean getIsCash() {
		return isCash;
	}

	public void setIsCash(boolean isCash) {
		this.isCash = isCash;
	}
    
    
    
    
}
