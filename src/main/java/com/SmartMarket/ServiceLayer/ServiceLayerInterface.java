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
	void addProduct(ProductsObject product);
	ProductsObject getProduct(int id);
	void deleteProduct(ProductsObject product);
	void updateProduct(ProductsObject product);
    String getPasword() throws CannotGetPassword;
	StoreDto getStore(); //bu  yerda foydalanuvchiga parolsiz Store jo'natiladi
	List<ProductsObject> getAllProducts();
	void updatePassword( String newPassword);
	void updateMonthFoyda( LocalDate date, long eski, long yeni);
	List<MonthFoyda> getMonthFoyda( LocalDate date);
	void updateProductMonthFoyda( int productId, int newValue);
	void updateStock(String barcode,String newStock);
	long foydaFindByStoreIdAndDateMonthYear( LocalDate date);
	void addSale(Sales s);
	Sales getSale(int id);
	List<Sales> getAllSale();
	List<Sales> getTodayAllSales( LocalDate date);
	void updateStore(StoreUpdateDto s);
	AuthResponse login(AuthRequest request);
}
