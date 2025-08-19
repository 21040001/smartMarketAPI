package com.SmartMarket.control;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SmartMarket.dto.TypeOfPayDTO;
import com.SmartMarket.serviceLayer.TypeOfPaysServiceInterface;
@RestController
@RequestMapping("/api/pays")
public class TypeOfPayController implements TypeOfPayControllerInterface{
	
	private TypeOfPaysServiceInterface pays;
	
	public TypeOfPayController(TypeOfPaysServiceInterface pays) {
		super();
		this.pays = pays;
	}

	@Override
	@GetMapping("/type")
	public ResponseEntity<List<TypeOfPayDTO>> getAllTypePays(@RequestParam String type) {
		List<TypeOfPayDTO> list = pays.getAllTypePays(type);
		if (list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	@GetMapping("/all")
	public ResponseEntity<List<TypeOfPayDTO>> getAll() {
		List<TypeOfPayDTO> list = pays.getAll();
		if (list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	@GetMapping("/cashier")
	public ResponseEntity<List<TypeOfPayDTO>> getAllPaysByKassier(@RequestParam String cashier) {
		List<TypeOfPayDTO> list = pays.getAllPaysByKassier(cashier);
		if (list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	@PostMapping("/add")
	public ResponseEntity<String> addPays(@RequestBody TypeOfPayDTO t) {
		pays.addPays(t);
		return ResponseEntity.status(HttpStatus.CREATED).body("Muvaffaqiyatli qo'shildi");
	}

	
}
