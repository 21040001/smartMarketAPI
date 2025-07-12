package com.SmartMarket.HibernateDAL;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SmartMarket.Entity.Stores;

import jakarta.transaction.Transactional;

// Bu interfeys do'konlar bilan ishlovchi data access qatlamidir
@Repository
public interface DALStoresInterface extends JpaRepository<Stores, Long> {

    /**
     * Berilgan store_id (do'kon ID) bo‘yicha parolni olish.
     * Bu yerda `nativeQuery = true` bo‘lgani uchun SQL to‘g‘ridan-to‘g‘ri yozilgan.
     * 
     * Misol: SELECT s.password FROM Stores s WHERE s.store_id = 1;
     */
    @Transactional
    @Query(value = "SELECT s.password FROM Stores s WHERE s.store_id = :storeId", nativeQuery = true)
    Optional<String> getPasword(@Param("storeId") int store_id);

    /**
     * Spring Data tomonidan avtomatik yoziladigan metod.
     * Bu metod `storeId` ustuni bo‘yicha do‘kon ma’lumotini qaytaradi.
     * JPQL (Java Persistence Query Language) asosida ishlaydi.
     */
    @Transactional
    Optional<Stores> findByStoreId(Integer storeId);// null qiymatlarni oldini olish uchun Optional sinifi bilan ishlayabmiz
}
