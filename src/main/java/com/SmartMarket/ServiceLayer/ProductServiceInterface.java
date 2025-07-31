package com.SmartMarket.ServiceLayer;

import java.util.List;

import com.SmartMarket.Entity.ProductsObject;

public interface ProductServiceInterface {
	void addProduct(ProductsObject product);
	ProductsObject getProduct(int id);
	void deleteProduct(ProductsObject product);
	void updateProduct(ProductsObject product);
	void updateProductMonthFoyda( int productId, int newValue);
	List<ProductsObject> getAllProducts();
	void updateStock(String barcode,String newStock);
}
