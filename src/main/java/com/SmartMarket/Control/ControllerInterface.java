package com.SmartMarket.Control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.Entity.SalesObject;
import com.SmartMarket.Entity.Stores;

public interface ControllerInterface {
	ResponseEntity<String> addProduct(ProductsObject product);
	ResponseEntity<List<Sales>> getTodaySales(int storeId, LocalDate todayDate, LocalDate tommorowDate);
	ResponseEntity<ProductsObject> getProduct(int storeId,int id);
	ResponseEntity<String> deleteProduct(ProductsObject product);
	ResponseEntity<String> updateProduct(ProductsObject product);
    ResponseEntity<String> getPasword(int store_id);
	ResponseEntity<Stores> getStore(int store_id);
	ResponseEntity<List<ProductsObject>> getAllProducts(int id);
	ResponseEntity<String> addSales(SalesObject sale);
	ResponseEntity<String> updatePassword(int store_id, String newPassword);
	ResponseEntity<String> updateMonthFoyda(int storeId, LocalDate date, long eski, long yeni);
	ResponseEntity<List<MonthFoyda>> getMonthFoyda(int storeId, LocalDate date);
	ResponseEntity<String> updateProductMonthFoyda(int storeId, int productId, int newValue);
}
