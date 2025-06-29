package com.SmartMarket.HibernateDAL;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SmartMarket.Entity.MonthFoyda;

// @Repository - bu interfeys ma'lumotlar bazasi qatlamiga tegishli ekanligini bildiradi
@Repository
public interface MonthInterface extends JpaRepository<MonthFoyda, Long> {

    /**
     * Oylik foydani yangilash yoki yangi yozuv kiritish.
     * Agar `store_id` va `date` mos tushsa => yangilanadi,
     * Agar topilmasa => yangi yozuv qo‘shiladi.
     *
     * MERGE INTO - bu SQL Server yoki ba’zi DBMS larda ishlatiladigan UPSERT amali
     */
    @Modifying
    @Query(value = """
        MERGE INTO month_foyda AS target
        USING (SELECT :storeId AS store_id, :date AS date) AS source
        ON (target.store_id = source.store_id AND target.date = source.date)
        WHEN MATCHED THEN 
            UPDATE SET month_foyda = :foyda
        WHEN NOT MATCHED THEN 
            INSERT (store_id, date, month_foyda)
            VALUES (:storeId, :date, :foyda);
        """, nativeQuery = true)
    void updateMonthFoyda(
        @Param("storeId") int storeId, 
        @Param("date") LocalDate date, 
        @Param("foyda") long foyda
    );

    /**
     * Berilgan `store_id` va `date` bo‘yicha, ushbu oyning barcha foyda yozuvlarini olib keladi.
     * Yil va oy `date` ustunidan olinadi.
     */
    @Query(value = """
        SELECT * FROM month_foyda 
        WHERE store_id = :storeId 
        AND YEAR(date) = YEAR(:date)
        AND MONTH(date) = MONTH(:date)
        """, nativeQuery = true)
    List<MonthFoyda> findByStoreIdAndDateMonthYear(
        @Param("storeId") int storeId, 
        @Param("date") LocalDate date
    );
    
    @Query(value="Select month_foyda from month_foyda where store_id = :storeId AND date = :date", nativeQuery= true)
    long foydaFindByStoreIdAndDateMonthYear(
    		@Param("storeId") int storeId, 
    		@Param("date") LocalDate date);
}
