package com.SmartMarket.hibernateDAL;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.entity.TypeOfPay;

@Repository
public interface DALTypeOfPay extends JpaRepository<TypeOfPay, Long> {
	

	@Transactional()
	@Query(value ="select * from pay_type_table where store_id=:storeId AND type_of_pay=:type", nativeQuery = true)
	List<TypeOfPay> getAllTypePays(@Param("storeId") int storeId, @Param("type") String type);
	
	@Transactional
	@Query(value ="select * from pay_type_table where store_id=:storeId ", nativeQuery = true)
	List<TypeOfPay> getAll(@Param("storeId") int storeId);
	
	@Transactional
	@Query(value ="select * from pay_type_table where store_id=:storeId AND which_cashier=:cashier", nativeQuery = true)
	List<TypeOfPay> getAllPaysByKassier(@Param("storeId") int storeId, @Param("cashier") String cashier);
}
