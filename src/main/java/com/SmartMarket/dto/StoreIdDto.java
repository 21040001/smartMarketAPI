package com.SmartMarket.dto;

public class StoreIdDto {
	private final  String username;
    private final int storeId;

    public StoreIdDto(String username, int storeId) {
        this.username = username;
        this.storeId = storeId;
    }

    public String getUsername() {
        return username;
    }

    public int getStoreId() {
        return storeId;
    }
}
