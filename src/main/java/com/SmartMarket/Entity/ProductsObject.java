package com.SmartMarket.Entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Products")
public class ProductsObject {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private int productId;
	@Column(name = "storeId")
	private String storeId;
	@Column(name = "barcode")
	private String barcode;
	@Column(name = "name")
	private String name;
	@Column(name = "stock")
	private String stock;
	@Column(name = "costPrice")
	private String costPrice;
	@Column(name = "salePrice")
	private String salePrice;
	@Column(name = "foyda")
	private String foyda;
	@Column(name = "monthFoyda")
	private Long monthFoyda;
	
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getFoyda() {
		return foyda;
	}
	public void setFoyda(String foyda) {
		this.foyda = foyda;
	}
	public Long getMonthFoyda() {
		return monthFoyda;
	}
	public void setMonthFoyda(Long monthFoyda) {
		this.monthFoyda = monthFoyda;
	}
	
	
	
}
