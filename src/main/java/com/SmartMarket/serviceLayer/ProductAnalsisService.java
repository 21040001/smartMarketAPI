package com.SmartMarket.serviceLayer;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.exceptions.CannotGetPassword;
import com.SmartMarket.hibernateDAL.ProductAnalsisDAL;

@Service
public class ProductAnalsisService implements ProductAnalsisInterface{

	private final ProductAnalsisDAL repo;
    
    
	
	public ProductAnalsisService(ProductAnalsisDAL repo) {
		super();
		this.repo = repo;
	}
	
	private int getCurrentStoreId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
        return principal.getStoreId();
    }


	@Override
	public List<Object[]> getTheMostSaleProduct(int day, int limit) {
		int storeId = getCurrentStoreId();
		return repo.getTheMostSaleProduct(day, limit, storeId).orElseThrow(() -> new CannotGetPassword(String.valueOf(storeId) + " Eng ko'p sotilgan mahsulot "));
	}

	@Override
	public List<Object[]> getTheLeastSaleProduct(int day, int limit) {
		int storeId = getCurrentStoreId();
		return repo.getTheLeastSaleProduct(day, limit, storeId).orElseThrow(() -> new CannotGetPassword(String.valueOf(storeId) + " Eng kam sotilgan mahsulot "));
	}

	@Override
	public List<Object[]> getSaleQuantityByHour(int day) {
		int storeId = getCurrentStoreId();
		return repo.getSaleQuantityByHour(day, storeId).orElseThrow(() -> new CannotGetPassword(String.valueOf(storeId) + " idli do'kon mahsulot bazli sotish miqdor "));
	}

}
