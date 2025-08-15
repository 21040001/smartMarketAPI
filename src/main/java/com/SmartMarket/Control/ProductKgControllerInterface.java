package com.SmartMarket.Control;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.SmartMarket.Entity.ProductKgObject;

public interface ProductKgControllerInterface {
	ResponseEntity<String> addProduct(ProductKgObject product);

	ResponseEntity<ProductKgObject> getProduct(String id);

	ResponseEntity<String> deleteProduct(ProductKgObject product);

	ResponseEntity<String> updateProduct(ProductKgObject product);

	ResponseEntity<List<ProductKgObject>> getAllProducts();

	ResponseEntity<String> updateStock(String barcode, String newStock);

}
