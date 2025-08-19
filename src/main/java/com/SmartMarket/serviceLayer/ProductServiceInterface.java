package com.SmartMarket.serviceLayer;

import java.util.List;

import com.SmartMarket.entity.ProductsObject;

public interface ProductServiceInterface {
	void addProduct(ProductsObject product);

	ProductsObject getProduct(String id);

	void deleteProduct(ProductsObject product);

	void updateProduct(ProductsObject product);

	List<ProductsObject> getAllProducts();

	void updateStock(String barcode, String newStock);
}
