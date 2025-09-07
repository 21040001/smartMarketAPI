package com.SmartMarket.control;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.SmartMarket.entity.Payment;

public interface PaymentControllerInterface {
	
	ResponseEntity<Payment> getCustomerLastPayment(int customerId );
	
	ResponseEntity<List<Payment>> getCustomerPayments(int customerId);
	
	ResponseEntity<List<Payment>> getStorePayments(int start, int end);
	
	ResponseEntity<List<Payment>> getPaymentsByType(String type, int start,int end);
	
	ResponseEntity<Double> getCustomerTotalPayment(int customerId);
	
	ResponseEntity<String> addPayment(Payment p);
	
	ResponseEntity<String> donePay( Long paymentId , Double amount,  String method ) ;
}
