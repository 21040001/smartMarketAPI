package com.SmartMarket.Control;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.SmartMarket.dto.TypeOfPayDTO;

public interface TypeOfPayControllerInterface {

	ResponseEntity<List<TypeOfPayDTO>> getAllTypePays( String type);
	ResponseEntity<List<TypeOfPayDTO>> getAll();
	ResponseEntity<List<TypeOfPayDTO>> getAllPaysByKassier( String cashier);
	ResponseEntity<String> addPays(TypeOfPayDTO t);
	
}
