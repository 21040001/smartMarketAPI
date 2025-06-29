// Kerakli kutubxonalarni import qilish
package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service; // Spring'ning servis annotatsiyasi

import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.Entity.Stores;
import com.SmartMarket.HibernateDAL.*;

import jakarta.transaction.Transactional;

// Service deb belgilanmoqda - bu sinf servis funksiyalarini bajaradi
@Service
public class ServiceLayer implements ServiceLayerInterface {

	// Private o'zgaruvchilar - kerakli data access layer interfeyslar
	private final DALProductInterface products;
	private final DALSalesInterface sale;
	private final DALStoresInterface store;
	private final MonthInterface month;
	
	// Konstruktor - xizmat qatlamiga DAL qatlamlarini yuboradi (dependency injection)
	public ServiceLayer(DALProductInterface products, DALSalesInterface sale,
			DALStoresInterface store, MonthInterface month) {
		super();
		this.products = products;
		this.sale = sale;
		this.store = store;
		this.month = month;
	}

	// Mahsulot qo'shish funksiyasi
	@Override
	@Transactional // Bitta butun tranzaksiyada amalga oshiriladi
	public void addProduct(ProductsObject product) {
		products.save(product); // Mahsulotni saqlash
	}

	// ID bo'yicha mahsulot olish funksiyasi
	@Override
	@Transactional
	public ProductsObject getProduct(int storeId, int id) {
		return products.findByStoreIdAndBarcode(String.valueOf(storeId), String.valueOf(id));
	}

	// Mahsulotni o'chirish funksiyasi
	@Override
	@Transactional
	public void deleteProduct(ProductsObject product) {
		products.delete(product);
	}

	// Mahsulotni yangilash funksiyasi
	@Override
	@Transactional
	public void updateProduct(ProductsObject product) {
		products.save(product); // `save()` bu yerda yangilashni ham bajaradi
	}

	// Do'kon parolini olish
	@Override
	public String getPasword(int store_id) {
		return store.getPasword(store_id);
	}

	// ID bo'yicha do'kon ma'lumotini olish
	@Override
	public Stores getStore(int store_id) {
		return store.findByStoreId(store_id);
	}

	// Barcha mahsulotlarni olish (do'kon ID bo'yicha)
	@Override
	@Transactional
	public List<ProductsObject> getAllProducts(int id) {
		return products.findByStoreId(String.valueOf(id));
	}

	

	// Do'kon parolini yangilash
	@Override
	public void updatePassword(int store_id, String newPassword) {
		Stores st = getStore(store_id); // Do'konni olish
		st.setPassword(newPassword);    // Yangi parol o'rnatish
		store.save(st);                 // Saqlash
	}

	// Oylik foydani yangilash (eski va yangi qiymatlarni qo‘shish orqali)
	@Override
	@Transactional
	public void updateMonthFoyda(int storeId, LocalDate date, long eski, long yangi) {
		long foyda = yangi + eski; // Yangi foyda hisoblash
		month.updateMonthFoyda(storeId, date, foyda);
	}

	// Do'kon va oy bo‘yicha foydani olish
	@Override
	@Transactional
	public List<MonthFoyda> getMonthFoyda(int storeId, LocalDate date) {
		return month.findByStoreIdAndDateMonthYear(storeId, date);
	}

	// Mahsulotga tegishli oylik foydani yangilash
	@Override
	@Transactional
	public void updateProductMonthFoyda(int storeId, int productId, int newValue) {
		products.updateMonthFoyda(
			String.valueOf(storeId),
			String.valueOf(productId),
			Long.valueOf(newValue)
		);
	}

	@Override
	public int updateStock(String storeId, String barcode, String newStock) {
		// TODO Auto-generated method stub
		return products.updateStock(storeId, barcode, newStock);
	}

	@Override
	public long foydaFindByStoreIdAndDateMonthYear(int storeId, LocalDate date) {
		// TODO Auto-generated method stub
		return month.foydaFindByStoreIdAndDateMonthYear(storeId, date);
	}

	@Override
	public void addSale(Sales s) {
		sale.save(s);
	}

	@Override
	public Sales getSale(int id) {
		return sale.getSale(id);
	}

	@Override
	public List<Sales> getAllSale(int storeId) {
		return sale.getAllSale(storeId);
	}

	@Override
	public List<Sales> getTodayAllSales(int storeId, LocalDate date) {
		LocalDate tomorow = date.plusDays(1);
		return sale.getTodayAllSales(storeId, date, tomorow);
	}

	@Override
	public void updateStore(Stores s) {
		store.save(s);
		
	}
}
