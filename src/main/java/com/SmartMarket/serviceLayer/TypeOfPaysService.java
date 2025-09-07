package com.SmartMarket.serviceLayer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.dto.TypeOfPayDTO;
import com.SmartMarket.entity.Payment;
import com.SmartMarket.entity.TypeOfPay;
import com.SmartMarket.hibernateDAL.DALTypeOfPay;

@Service
public class TypeOfPaysService implements TypeOfPaysServiceInterface {
	
	private DALTypeOfPay pay;
	private final ModelMapper modelMapper;
	private PaymentsService service;
	private CustomerService customer;
	
	

	public TypeOfPaysService(DALTypeOfPay pay, ModelMapper modelMapper, PaymentsService service,
			CustomerService customer) {
		super();
		this.pay = pay;
		this.modelMapper = modelMapper;
		this.service = service;
		this.customer = customer;
	}

	private int getCurrentStoreId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
        return principal.getStoreId();
    }
	
	@Override
	public List<TypeOfPayDTO> getAllTypePays(String type) {
		int storeId = getCurrentStoreId();
		List<TypeOfPay> typeOfPays = pay.getAllTypePays(storeId, type);
		return typeOfPays.stream()
	            .map(entity -> modelMapper.map(entity, TypeOfPayDTO.class))
	            .collect(Collectors.toList()); 
	}

	@Override
	public List<TypeOfPayDTO> getAll() {
	    int storeId = getCurrentStoreId();

	    // Entity listesi geliyor
	    List<TypeOfPay> typeOfPays = pay.getAll(storeId);

	    // Entity → DTO 
	    return typeOfPays.stream()
	            .map(entity -> modelMapper.map(entity, TypeOfPayDTO.class))
	            .collect(Collectors.toList());
	}


	@Override
	public List<TypeOfPayDTO> getAllPaysByKassier( String cashier) {
		int storeId = getCurrentStoreId();
		 List<TypeOfPay> typeOfPays = pay.getAllPaysByKassier(storeId, cashier);
		return typeOfPays.stream()
	            .map(entity -> modelMapper.map(entity, TypeOfPayDTO.class))
	            .collect(Collectors.toList());
	}

	@Override
	public void addPays(TypeOfPayDTO t, String note, Long customerId) {
		int storeId = getCurrentStoreId();
		TypeOfPay object = modelMapper.map(t, TypeOfPay.class);
		object.setStoreId(storeId);
		object.setId(0);
		TypeOfPay saved = pay.save(object);
		System.out.println("id=>"+customerId);
		if(t.getTypeOfPay().equalsIgnoreCase("debt")) {
			Payment p = new Payment();
			LocalDate today = LocalDate.now();
			BigDecimal b = new BigDecimal(t.getTotalAmmaount());
			p.setAmount(b);
			p.setCustomerId(customerId);
			p.setInvoiceId(saved.getId());
			p.setMethod("unpaid");
			p.setNotes(note);
			p.setPaidAmount(BigDecimal.ZERO);
			p.setPaymentDate(today);
			p.setPaymentId(0L);
			p.setStoreId(storeId);
			p.setType("debt");
			service.addPayment(p);
			customer.addDebt(customerId, t.getTotalAmmaount());
		}
	}

}
