package com.SmartMarket.serviceLayer;

import com.SmartMarket.dto.AuthRequest;
import com.SmartMarket.dto.AuthResponse;

public interface ServiceLayerInterface {
	
	AuthResponse login(AuthRequest request);
}
