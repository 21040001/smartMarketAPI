package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.Entity.Stores;
import com.SmartMarket.HibernateDAL.*;

@Service
public class ServiceLayer implements ServiceLayerInterface{

	private final DALProductInterface products;
	private final DALSalesInterface sale;
	private final DALStoresInterface store;
	
	public ServiceLayer(DALProductInterface products, DALSalesInterface sale,
			DALStoresInterface store) {
		super();
		this.products = products;
		this.sale = sale;
		this.store = store;
	}

	@Override
	public void addProduct(ProductsObject product) {
		products.addProduct(product);
	}

	@Override
	public ProductsObject getProduct(int id) {
		return products.getProduct(id);
	}

	@Override
	public void deleteProduct(ProductsObject product) {
		products.deleteProduct(product);
	}

	@Override
	public void updateProduct(ProductsObject product) {
		products.updateProduct(product);
	}

	

	@Override
	public String getPasword(int store_id) {
		// TODO Auto-generated method stub
		return store.getPasword(store_id);
	}

	@Override
	public Stores getStore(int store_id) {
		// TODO Auto-generated method stub
		return store.getStore(store_id);
	}

	@Override
	public List<ProductsObject> getAllProducts(int id) {
		
		return products.getAllProducts(id);
	}

	@Override
	public void addSales(Sales sales) {
		sale.addSales(sales);
		
	}

	@Override
	public List<Sales> getTodaySales(int storeId, LocalDate todayDate, LocalDate tommorowDate) {
		
		return sale.getTodaySales(storeId, todayDate, tommorowDate);
	}

	@Override
	public void updatePassword(int store_id, String newPassword) {
		store.updatePassword(store_id, newPassword);
		
	}
	
	
}
