package com.SmartMarket.hibernateDAL;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.entity.Customer;

@Repository
public interface DALCustomerInterface extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT c FROM customers  WHERE store_id = :storeId AND customer_id = :customerId", nativeQuery = true)
    Customer getCustomerById(@Param("storeId") String storeId, @Param("customerId") long customerId);

    @Query(value = "SELECT * FROM customers  WHERE store_id = :storeId ", nativeQuery=true)
    List<Customer> getAllCustomer(@Param("storeId") String storeId);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE customers  SET amount = amount - :debt WHERE customer_id = :customerId AND store_id = :storeId" , nativeQuery = true)
    void deleteDebt(@Param("storeId") String storeId, @Param("customerId") Long customerId, @Param("debt") double debt);

    @Modifying
    @Transactional
    @Query(value = "UPDATE customers SET amount = amount + :debt WHERE customer_id = :customerId AND store_id = :storeId" , nativeQuery = true )
    void addDebt(@Param("storeId") String storeId, @Param("customerId") Long customerId, @Param("debt") double debt);
}
