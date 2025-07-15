package com.SmartMarket.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class LoginRequestDto {
	@NotNull
    @NotEmpty
	private String username;
	@NotNull
    @NotEmpty
	private String password;
	@NotNull
    @Pattern(regexp = "[0-9]")
	private Long storeId;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	} 
	

	
}
