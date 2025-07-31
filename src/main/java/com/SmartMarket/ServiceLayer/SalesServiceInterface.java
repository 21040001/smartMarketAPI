package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.util.List;

import com.SmartMarket.Entity.Sales;

public interface SalesServiceInterface {
	void addSale(Sales s);
	Sales getSale(int id);
	List<Sales> getAllSale();
	List<Sales> getTodayAllSales( LocalDate date);
}
