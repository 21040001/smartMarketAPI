package com.SmartMarket.control;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.SmartMarket.dto.StoreDto;
import com.SmartMarket.dto.StoreUpdateDto;
import com.SmartMarket.entity.MonthFoyda;
import com.SmartMarket.entity.ProductsObject;
import com.SmartMarket.entity.Sales;

public interface ControllerInterface {
	ResponseEntity<String> addProduct(ProductsObject product);
	ResponseEntity<ProductsObject> getProduct(String id);
	ResponseEntity<String> deleteProduct(ProductsObject product);
	ResponseEntity<String> updateProduct(ProductsObject product);
	ResponseEntity<StoreDto> getStore();
	ResponseEntity<List<ProductsObject>> getAllProducts();
	ResponseEntity<String> updatePassword( String newPassword);
	ResponseEntity<String> updateMonthFoyda( LocalDate date, long eski, long yeni);
	ResponseEntity<List<MonthFoyda>> getMonthFoyda( LocalDate date);
	ResponseEntity<String>updateStock(String barcode,String newStock);
	ResponseEntity<Long> foydaFindByStoreIdAndDateMonthYear( LocalDate date);
	ResponseEntity<String> addSale(Sales s);
	ResponseEntity<Sales> getSale(int id);
	ResponseEntity<List<Sales>> getAllSale();
	ResponseEntity<List<Sales>> getTodayAllSales( LocalDate date);
	ResponseEntity<String> updateStore(StoreUpdateDto s);
}
