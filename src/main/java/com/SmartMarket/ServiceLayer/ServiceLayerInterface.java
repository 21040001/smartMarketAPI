package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.util.List;


import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.dto.AuthRequest;
import com.SmartMarket.dto.AuthResponse;
import com.SmartMarket.dto.StoreDto;
import com.SmartMarket.dto.StoreUpdateDto;
import com.SmartMarket.exceptions.CannotGetPassword;

public interface ServiceLayerInterface {
	
	
     //bu  yerda foydalanuvchiga parolsiz Store jo'natiladi
	
	
	
	
	
	
	
	
	AuthResponse login(AuthRequest request);
}
