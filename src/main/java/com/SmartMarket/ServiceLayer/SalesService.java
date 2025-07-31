package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.Entity.Sales;
import com.SmartMarket.HibernateDAL.DALSalesInterface;
import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.exceptions.SaleNotFoundException;

@Service
public class SalesService implements SalesServiceInterface{

    private final DALSalesInterface sale;
    
	public SalesService(DALSalesInterface sale) {
		super();
		this.sale = sale;
	}

	// 🔐 Kullanıcıdan storeId'yi token'dan al
    private int getCurrentStoreId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
        return principal.getStoreId();
    }
	
    @Override
    @Transactional
    public void addSale(Sales s) {
        sale.save(s);
    }

    @Override
    @Transactional(readOnly = true)
    public Sales getSale(int id) {
        return sale.getSale(id)
                .orElseThrow(() -> new SaleNotFoundException(String.valueOf(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sales> getAllSale() {
        int storeId = getCurrentStoreId();
        return sale.getAllSale(storeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sales> getTodayAllSales(LocalDate date) {
        int storeId = getCurrentStoreId();
        return sale.getTodayAllSales(storeId, date, date.plusDays(1));
    }

}
