package com.SmartMarket.serviceLayer;



import java.util.List;

import com.SmartMarket.entity.Customer;

public interface CustomerServiceInterface {

	List<Customer> getAllCustomer();
	void addCustomer(Customer customer);
	void updateCustomer(Customer customer);
	void deleteCustomer(Customer customer);
	Customer getCustomer(long id);
	void addDebt(Long customerId, double newBlance);
	void deleteDebt(Long customerId, double newBlance);
}
