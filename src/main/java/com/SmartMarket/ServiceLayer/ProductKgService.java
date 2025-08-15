package com.SmartMarket.ServiceLayer;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.Entity.ProductKgObject;
import com.SmartMarket.HibernateDAL.DALProductKgInterface;
import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.exceptions.ProductNotFoundException;

@Service
public class ProductKgService implements ProductKgInterface {

	private final DALProductKgInterface products;

	public ProductKgService(DALProductKgInterface products) {
		super();
		this.products = products;
	}

	// 🔐 Kullanıcıdan storeId'yi token'dan al
	private int getCurrentStoreId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
		return principal.getStoreId();
	}

	@Override
	@Transactional
	public void addProduct(ProductKgObject product) {
		products.save(product);
	}

	@Override
	@Transactional
	public ProductKgObject getProduct(String id) {
		int storeId = getCurrentStoreId();
		return products.findByStoreIdAndBarcode(String.valueOf(storeId), id)
				.orElseThrow(() -> new ProductNotFoundException(String.valueOf(id)));
	}

	@Override
	@Transactional
	public void deleteProduct(ProductKgObject product) {
		products.delete(product);
	}

	@Override
	@Transactional
	public void updateProduct(ProductKgObject product) {
		products.save(product);
	}


	@Override
	@Transactional(readOnly = true)
	public List<ProductKgObject> getAllProducts() {
		int storeId = getCurrentStoreId();
		return products.findByStoreId(String.valueOf(storeId));
	}

	@Override
	@Transactional
	public void updateStock(String barcode, String newStock) {
		int storeId = getCurrentStoreId();
		products.updateStock(String.valueOf(storeId), barcode, newStock);
	}
}
