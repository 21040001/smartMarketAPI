package com.SmartMarket.serviceLayer;

import com.SmartMarket.dto.StoreDto;
import com.SmartMarket.dto.StoreUpdateDto;
import com.SmartMarket.exceptions.CannotGetPassword;

public interface StoresServiceInterface {
	String getPasword() throws CannotGetPassword;
	StoreDto getStore();
	void updatePassword( String newPassword);
	void updateStore(StoreUpdateDto s);
	void updateChatId(int storeId,long chatId);
	Long getChatId(int storeId);
}
