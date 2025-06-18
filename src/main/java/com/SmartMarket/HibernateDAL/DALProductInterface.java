package com.SmartMarket.HibernateDAL;

import java.util.List;


import com.SmartMarket.Entity.ProductsObject;

public interface DALProductInterface {
	void addProduct(ProductsObject product);
	ProductsObject getProduct(int storeId,int id);
	void deleteProduct(ProductsObject product);
	void updateProduct(ProductsObject product);
	List<ProductsObject> getAllProducts(int id);
	void updateProductMonthFoyda(int storeId, int productId, int newValue);
}
