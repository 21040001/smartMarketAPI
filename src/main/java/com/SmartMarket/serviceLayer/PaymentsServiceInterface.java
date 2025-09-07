package com.SmartMarket.serviceLayer;

import java.util.List;


import com.SmartMarket.entity.Payment;

public interface PaymentsServiceInterface {

	Payment getCustomerLastPayment(int customerId );
	
	List<Payment> getCustomerPayments(int customerId);
	
	List<Payment> getStorePayments(int start, int end);
	
	List<Payment> getPaymentsByType(String type, int start,int end);
	
	double getCustomerTotalPayment(int customerId);
	
	void addPayment(Payment p);
	
	void donePay( Long paymentId , Double amount,  String method ) ;
}
