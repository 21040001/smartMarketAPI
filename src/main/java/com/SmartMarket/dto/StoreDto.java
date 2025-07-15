package com.SmartMarket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class StoreDto {
	
	@NotNull
	private int storeId;
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[a-zA-Z0-9\\s]{3,20}", message = "Ism 3 va 20 ta belgi orasida bo'lishi kerak")
	private String name;
	@NotNull
	@NotEmpty(message = "Manzil bo'sh bo'lishi mumkin emas")
	private String adress;
	@NotNull
	@NotEmpty
	@Email(message = "Email manzili noto'g'ri")
	private String email;
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[0-9+\\-\\s]+", message = "Telefon raqami noto'g'ri")
	private String number;
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	
}
