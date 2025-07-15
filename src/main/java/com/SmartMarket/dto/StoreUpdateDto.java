package com.SmartMarket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class StoreUpdateDto {
	
	@NotNull
    private int storeId;
	@NotNull
    @NotEmpty(message = "Ism bo'sh bo'lishi mumkin emas")
    private String name;
	@NotNull
    @NotEmpty
    @Email(message="Email manzili noto'g'ri")
    private String email;
	@NotNull
    @NotEmpty(message = "Manzil bo'sh bo'lishi mumkin emas")
    private String adress;
	@NotNull
    @NotEmpty(message = "Telefon raqami bo'sh bo'lishi mumkin emas")
    private String number;
	@NotNull
    @NotEmpty(message = "Parol bo'sh bo'lishi mumkin emas")
    private String password; // frontend'den düz metin olarak gelir
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
