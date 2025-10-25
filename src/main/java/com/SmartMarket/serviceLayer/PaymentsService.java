package com.SmartMarket.serviceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.entity.Payment;
import com.SmartMarket.hibernateDAL.DALPaymentsInterface;

@Service
public class PaymentsService implements PaymentsServiceInterface{
	
	@Autowired
	private DALPaymentsInterface repo;
	
	
	private int getCurrentStoreId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
		int storeId = principal.getStoreId();
		return storeId;
	}

	@Override
	public Payment getCustomerLastPayment(int customerId) {
		return repo.getCustomerLastPayment(getCurrentStoreId(), customerId);
	}

	@Override
	public List<Payment> getCustomerPayments(int customerId) {
		return repo.getCustomerPayments(getCurrentStoreId(), customerId);
	}

	@Override
	public List<Payment> getStorePayments(int start, int end) {
		return repo.getStorePayments(getCurrentStoreId(), start, end);
	}

	@Override
	public List<Payment> getPaymentsByType(String type, int start, int end) {
		// TODO Auto-generated method stub
		return repo.getPaymentsByType(getCurrentStoreId(), type, start, end);
	}

	@Override
	public double getCustomerTotalPayment(int customerId) {
		return repo.getCustomerTotalPayment(getCurrentStoreId(), customerId);
	}

	@Override
	public void addPayment(Payment p) {
		p.setStoreId(getCurrentStoreId());
		repo.save(p);
	}

	@Override
	public void donePay(Long paymentId, Double amount, String method) {
		repo.donePay(paymentId, amount, method);
	}
	
	

}
