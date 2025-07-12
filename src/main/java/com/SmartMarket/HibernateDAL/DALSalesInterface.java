package com.SmartMarket.HibernateDAL;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SmartMarket.Entity.Sales;


@Repository
public interface DALSalesInterface extends JpaRepository<Sales, Long> {
	
	@Query(value="Select * from sales where id=:saleId" 
			, nativeQuery=true)
	Sales getSale(@Param("saleId") int id);
	
	@Query(value="Select * from sales where store_id=:storeId" 
			, nativeQuery=true)
	List<Sales> getAllSale(@Param("storeId") int storeId);
	
	@Query(value="Select * from sales where store_id=:storeId and sale_date  between :startDate and :tomorow " 
			, nativeQuery=true)
	List<Sales> getTodayAllSales(@Param("storeId")int storeId, @Param("startDate")LocalDate date, @Param("tomorow")LocalDate tomorow );
}
