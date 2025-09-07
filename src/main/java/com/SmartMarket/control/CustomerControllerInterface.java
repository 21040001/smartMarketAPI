package com.SmartMarket.control;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.SmartMarket.entity.Customer;


public interface CustomerControllerInterface {
	ResponseEntity<List<Customer>> getAllCustomer();

	ResponseEntity<String> addCustomer(Customer customer);

	ResponseEntity<String> updateCustomer(Long id , Customer customer);

	ResponseEntity<String> deleteCustomer(Long id);

	ResponseEntity<Customer> getCustomer(long customerId);

	ResponseEntity<String> addDebt(Long customerId, double newBlance);

	ResponseEntity<String> deleteDebt(Long customerId, double newBlance);
}
