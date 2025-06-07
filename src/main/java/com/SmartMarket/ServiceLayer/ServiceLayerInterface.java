package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.Entity.Stores;

public interface ServiceLayerInterface {
	void addProduct(ProductsObject product);
	List<Sales> getTodaySales(int storeId, LocalDate todayDate, LocalDate tommorowDate);
	ProductsObject getProduct(int id);
	void deleteProduct(ProductsObject product);
	void updateProduct(ProductsObject product);
    String getPasword(int store_id);
	Stores getStore(int store_id);
	List<ProductsObject> getAllProducts(int id);
	void addSales(Sales sales);
	void updatePassword(int store_id, String newPassword);
}
