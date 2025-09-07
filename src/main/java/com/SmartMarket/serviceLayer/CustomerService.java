package com.SmartMarket.serviceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.entity.Customer;
import com.SmartMarket.hibernateDAL.DALCustomerInterface;

@Service
public class CustomerService implements CustomerServiceInterface{

	
	private DALCustomerInterface repo;
	
	public CustomerService(DALCustomerInterface repo) {
		super();
		this.repo = repo;
	}

	private String getCurrentStoreId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
		String storeId =String.valueOf(principal.getStoreId());
		return storeId;
	}
	
	@Override
	public void addCustomer(Customer customer) {
		customer.setStoreId(getCurrentStoreId());
		repo.save(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		customer.setStoreId(getCurrentStoreId());
		repo.save(customer);
	}

	@Override
	public void deleteCustomer(Customer customer) {
		repo.delete(customer);
	}

	@Override
	public Customer getCustomer( long customerId) {
		Customer customer = repo.getCustomerById(getCurrentStoreId(), customerId);
		return customer;
	}

	@Override
	public void addDebt(Long customerId, double newBlance) {
		repo.addDebt(getCurrentStoreId(), customerId, newBlance);
	}

	@Override
	public void deleteDebt(Long customerId, double newBlance) {
		repo.deleteDebt(getCurrentStoreId(), customerId, newBlance);
	}

	@Override
	public List<Customer> getAllCustomer() {
		return repo.getAllCustomer(getCurrentStoreId());
	}

}
