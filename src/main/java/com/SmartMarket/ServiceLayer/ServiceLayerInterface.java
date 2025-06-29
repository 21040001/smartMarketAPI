package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.util.List;


import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.Entity.Stores;

public interface ServiceLayerInterface {
	void addProduct(ProductsObject product);
	ProductsObject getProduct(int storeId,int id);
	void deleteProduct(ProductsObject product);
	void updateProduct(ProductsObject product);
    String getPasword(int store_id);
	Stores getStore(int store_id);
	List<ProductsObject> getAllProducts(int id);
	void updatePassword(int store_id, String newPassword);
	void updateMonthFoyda(int storeId, LocalDate date, long eski, long yeni);
	List<MonthFoyda> getMonthFoyda(int storeId, LocalDate date);
	void updateProductMonthFoyda(int storeId, int productId, int newValue);
	int updateStock(String storeId,String barcode,String newStock);
	long foydaFindByStoreIdAndDateMonthYear(int storeId, LocalDate date);
	void addSale(Sales s);
	void updateStore(Stores s);
	Sales getSale(int id);
	List<Sales> getAllSale(int storeId);
	List<Sales> getTodayAllSales(int storeId, LocalDate date);
}
