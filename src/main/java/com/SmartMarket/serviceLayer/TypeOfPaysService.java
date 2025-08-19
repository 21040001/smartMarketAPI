package com.SmartMarket.serviceLayer;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.dto.TypeOfPayDTO;
import com.SmartMarket.entity.TypeOfPay;
import com.SmartMarket.hibernateDAL.DALTypeOfPay;

@Service
public class TypeOfPaysService implements TypeOfPaysServiceInterface {
	
	private DALTypeOfPay pay;
	private final ModelMapper modelMapper;

	public TypeOfPaysService(DALTypeOfPay pay, ModelMapper modelMapper) {
		super();
		this.pay = pay;
		this.modelMapper = modelMapper;
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
	public void addPays(TypeOfPayDTO t) {
		int storeId = getCurrentStoreId();
		TypeOfPay object = modelMapper.map(t, TypeOfPay.class);
		object.setStoreId(storeId);
		object.setId(0);
		pay.save(object);
	}

}
