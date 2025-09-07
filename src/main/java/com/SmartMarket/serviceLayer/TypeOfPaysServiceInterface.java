package com.SmartMarket.serviceLayer;

import java.util.List;

import com.SmartMarket.dto.TypeOfPayDTO;

public interface TypeOfPaysServiceInterface {

	List<TypeOfPayDTO> getAllTypePays( String type);
	List<TypeOfPayDTO> getAll();
	List<TypeOfPayDTO> getAllPaysByKassier( String cashier);
	void addPays(TypeOfPayDTO t, String note, Long customerId);
}
