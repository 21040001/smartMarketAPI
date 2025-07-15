package com.SmartMarket.Entity;


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
@Table(name="Products")
public class ProductsObject {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private int productId;
	
	@NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]+", message = "Do'kon ID faqat raqamlardan iborat bo'lishi kerak")
	@Column(name = "storeId")
	private String storeId;
	
	@NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Shtrix kod noto'g'ri formatda")
	@Column(name = "barcode")
	private String barcode;
	
	@NotNull
    @NotEmpty(message = "Mahsulot nomi bo'sh bo'lishi mumkin emas")
	@Column(name = "name")
	private String name;
	
	@NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]+", message = "Zaxira faqat raqam bo'lishi kerak")
	@Column(name = "stock")
	private String stock;
	
	@NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9.]+", message = "Tan narxi noto'g'ri")
	@Column(name = "costPrice")
	private String costPrice;
	
	@NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9.]+", message = "Sotuv narxi noto'g'ri")
	@Column(name = "salePrice")
	private String salePrice;
	
	@NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9.]+", message = "Foyda noto'g'ri")
	@Column(name = "foyda")
	private String foyda;
	
	@NotNull
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
