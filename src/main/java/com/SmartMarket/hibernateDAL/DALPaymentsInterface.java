package com.SmartMarket.hibernateDAL;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.entity.Payment;

@Repository
public interface DALPaymentsInterface extends JpaRepository<Payment, Long> {

	@Query(value = "Select TOP 1 * From payments where store_id = :storeId And customer_id = :customerId ORDER BY payment_date DESC", nativeQuery = true)
	Payment getCustomerLastPayment(@Param("storeId") int storeId, @Param("customerId") int customerId);

	@Query(value = "Select Top 50 * From payments where store_id = :storeId And customer_id = :customerId", nativeQuery = true)
	List<Payment> getCustomerPayments(@Param("storeId") int storeId, @Param("customerId") int customerId);

	@Query(value = "SELECT * FROM payments where store_id = :storeId and type != 'debt' ORDER BY  payment_date DESC OFFSET :start ROWS FETCH NEXT :end ROWS ONLY", nativeQuery = true)
	List<Payment> getStorePayments(@Param("storeId") int storeId, @Param("start") int start, @Param("end") int end);

	@Query(value = "Select  * From payments where store_id = :storeId and type = :type ORDER BY  payment_date DESC OFFSET :start ROWS FETCH NEXT :end ROWS ONLY", nativeQuery = true)
	List<Payment> getPaymentsByType(@Param("storeId") int storeId, @Param("type") String type,
			@Param("start") int start, @Param("end") int end);

	@Query(value = "Select SUM(amount) From payments where store_id = :storeId and customer_id = :customerId", nativeQuery = true)
	double getCustomerTotalPayment(@Param("storeId") int storeId, @Param("customerId") int customerId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE payments SET paid_amount = CASE WHEN paid_amount = amount THEN amount ELSE paid_amount + :amount END, method = :method, type = CASE WHEN amount = (CASE WHEN paid_amount = :amount THEN 10 ELSE paid_amount + :amount END) THEN 'paid' ELSE 'debt' END WHERE payment_id = :paymentId", nativeQuery = true)
	void donePay(@Param("paymentId") Long paymentId, @Param("amount") Double amount, @Param("method") String method);

}
