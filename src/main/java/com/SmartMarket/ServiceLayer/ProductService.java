package com.SmartMarket.ServiceLayer;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.HibernateDAL.DALProductInterface;
import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.exceptions.ProductNotFoundException;

@Service
public class ProductService implements ProductServiceInterface{
	
	private final DALProductInterface products;

	public ProductService(DALProductInterface products) {
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
	public void addProduct(ProductsObject product) {
		products.save(product);
	}

	@Override
	@Transactional
	public ProductsObject getProduct(int id) {
		 int storeId = getCurrentStoreId();
	        return products.findByStoreIdAndBarcode(String.valueOf(storeId), String.valueOf(id))
	                .orElseThrow(() -> new ProductNotFoundException(String.valueOf(id)));
	}

	@Override
	@Transactional
	public void deleteProduct(ProductsObject product) {
		products.delete(product);
	}

	@Override
	@Transactional
	public void updateProduct(ProductsObject product) {
		products.save(product);
	}

	@Override
	@Transactional
	public void updateProductMonthFoyda(int productId, int newValue) {
		int storeId = getCurrentStoreId();
        products.updateMonthFoyda(String.valueOf(storeId), String.valueOf(productId), (long) newValue);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductsObject> getAllProducts() {
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
