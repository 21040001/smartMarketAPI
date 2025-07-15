package com.SmartMarket.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="Stores")
public class Stores {
	@Id
	@Column(name = "storeId")
	private int storeId;
	
	@NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9 ]+", message = "Yaroqsiz karakterlar mavjud.")
	@Column(name = "name")
	private String name;
	
	@NotNull
    @NotEmpty
	@Column(name = "adress")
	private String adress;
	
	@NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]")
	@Column(name = "password")
	private String password;
	
	@NotNull
    @NotEmpty
    @Email(message = "Email xato. Yaroqli Email manzili kiriting.")
	@Column(name = "email")
	private String email;
	
	@NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]")
	@Column(name = "number")
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
