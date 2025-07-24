package com.SmartMarket.ServiceLayer;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SmartMarket.HibernateDAL.ProductAnalsisDAL;
import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.exceptions.CannotGetPassword;
import com.SmartMarket.security.CustomUserDetailsService;
import com.SmartMarket.security.JwtTokenProvider;

@Service
public class ProductAnalsisService implements ProductAnalsisInterface{

	private final ProductAnalsisDAL repo;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    
    
	
	public ProductAnalsisService(ProductAnalsisDAL repo, JwtTokenProvider jwtTokenProvider,
			CustomUserDetailsService userDetailsService, ModelMapper modelMapper) {
		super();
		this.repo = repo;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
		this.modelMapper = modelMapper;
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
