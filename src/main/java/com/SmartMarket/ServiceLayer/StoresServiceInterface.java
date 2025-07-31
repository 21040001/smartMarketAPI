package com.SmartMarket.ServiceLayer;

import com.SmartMarket.dto.StoreDto;
import com.SmartMarket.dto.StoreUpdateDto;
import com.SmartMarket.exceptions.CannotGetPassword;

public interface StoresServiceInterface {
	String getPasword() throws CannotGetPassword;
	StoreDto getStore();
	void updatePassword( String newPassword);
	void updateStore(StoreUpdateDto s);
}
