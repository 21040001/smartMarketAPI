package com.SmartMarket.ServiceLayer;

import java.util.List;


import com.SmartMarket.Entity.ProductKgObject;

public interface ProductKgInterface {
	void addProduct(ProductKgObject product);

	ProductKgObject getProduct(String id);

	void deleteProduct(ProductKgObject product);

	void updateProduct(ProductKgObject product);

	List<ProductKgObject> getAllProducts();

	void updateStock(String barcode, String newStock);
}
