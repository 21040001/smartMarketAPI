package com.SmartMarket.hibernateDAL;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.entity.ProductKgObject;

// Bu interface mahsulotlar bilan ishlovchi data access layer bo‘lib xizmat qiladi
@Repository
public interface DALProductKgInterface extends JpaRepository<ProductKgObject, Long> {

	/**
	 * Ma'lum bir do‘kon (storeId) va mahsulot shtrix kodi (barcode) bo‘yicha
	 * mahsulotni topish
	 */
	@Query("SELECT p FROM ProductKgObject p WHERE p.barcode = :barcode AND p.storeId = :storeId")
	Optional<ProductKgObject> findByStoreIdAndBarcode(@Param("storeId") String storeId,
			@Param("barcode") String barcode);

	/**
	 * Do‘kon ID bo‘yicha barcha mahsulotlarni olish (Spring Data avtomatik
	 * yaratadi)
	 */
	List<ProductKgObject> findByStoreId(String storeId);


	/**
	 * Mahsulotning ombordagi mavjud sonini (stock) yangilash `int` qaytaradi — bu
	 * o‘zgartirilgan satrlar soni
	 */
	@Modifying
	@Transactional
	@Query("UPDATE ProductKgObject p SET p.stock = :newStock WHERE p.storeId = :storeId AND p.barcode = :barcode")
	void updateStock(@Param("storeId") String storeId, @Param("barcode") String barcode,
			@Param("newStock") String newStock);

	/**
	 * Berilgan barcode (shtrix kod) bo‘yicha barcha mahsulotlarni olish
	 */
	List<ProductKgObject> findByBarcode(String barcode);

	/**
	 * Mahsulotlarni narx oralig‘i bo‘yicha olish E'tibor bering: `salePrice` raqam
	 * bo‘lsa, parametrlar `String` emas, `Double` bo‘lishi maqsadga muvofiq.
	 */
	@Query("SELECT p FROM ProductKgObject p WHERE p.salePrice > :minPrice AND p.salePrice < :maxPrice")
	List<ProductKgObject> findByPriceRange(@Param("minPrice") String minPrice, @Param("maxPrice") String maxPrice);
}
