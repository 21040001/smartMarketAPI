package com.SmartMarket.serviceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.entity.ProductsObject;
import com.SmartMarket.exceptions.ProductNotFoundException;
import com.SmartMarket.hibernateDAL.DALProductInterface;
import com.SmartMarket.telegramBot.TelegramBot;

@Service
public class ProductService implements ProductServiceInterface {

	private final DALProductInterface products;
	@Autowired
	private TelegramBot bot;

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

	// 🔐 Kullanıcıdan storeId'yi token'dan al
	private String getCurrentUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
		return principal.getUsername();
	}

	@Override
	@Transactional
	public void addProduct(ProductsObject product) {
		products.save(product);
		bot.sendMessage(getCurrentStoreId(), "➕🛒 [" + getCurrentUserName()
				+ "] yangi mahsulot qo‘shdi. Qo'shilgan mahsulot barkod raqami: " + product.getBarcode() + " ✅");
	}

	@Override
	@Transactional
	public ProductsObject getProduct(String id) {
		int storeId = getCurrentStoreId();
		return products.findByStoreIdAndBarcode(String.valueOf(storeId), id)
				.orElseThrow(() -> new ProductNotFoundException(String.valueOf(id)));
	}

	@Override
	@Transactional
	public void deleteProduct(ProductsObject product) {
		products.delete(product);
		bot.sendMessage(getCurrentStoreId(), "🗑️ [" + getCurrentUserName()
				+ "] mahsulot o‘chirdi. O'chirilgan mahsulot barkod raqami: " + product.getBarcode() + " ❌");
	}

	@Override
	@Transactional
	public void updateProduct(ProductsObject product) {
		products.save(product);
		bot.sendMessage(getCurrentStoreId(),
				"✏️📦 [" + getCurrentUserName()
						+ "] mahsulot ma’lumotlarini yangiladi. Yangilangan mahsulot barkod raqami:  "
						+ product.getBarcode() + " ✅");
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
