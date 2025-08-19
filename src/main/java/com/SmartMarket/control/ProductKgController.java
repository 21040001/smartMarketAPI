package com.SmartMarket.control;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SmartMarket.entity.ProductKgObject;
import com.SmartMarket.serviceLayer.ProductKgInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/kg/")
public class ProductKgController implements ProductKgControllerInterface {

	private final ProductKgInterface products;

	public ProductKgController(ProductKgInterface products) {
		this.products = products;
	}

	/* Mahsulotlar bilan ishlash */
	// Yangi mahsulot qo'shish (Admin va Super admin uchun)
	@Override
	@PreAuthorize("hasAnyRole('ADMIN','SUPER')")
	@PostMapping("/products")
	public ResponseEntity<String> addProduct(@Valid @RequestBody ProductKgObject product) {
		products.addProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body("Mahsulot muvaffaqiyatli qo'shildi");
	}

	// Mahsulotni olish (Admin, Super admin va Ko'ruvchilar uchun)
	@Override
	@PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductKgObject> getProduct(@PathVariable("id") String id) {
		ProductKgObject product = products.getProduct(id);
		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Mahsulotni o'chirish (Admin va Super admin uchun)
	@Override
	@PreAuthorize("hasAnyRole('ADMIN','SUPER')")
	@DeleteMapping("/products")
	public ResponseEntity<String> deleteProduct(@Valid @RequestBody ProductKgObject product) {
		products.deleteProduct(product);
		return ResponseEntity.ok("Mahsulot o'chirildi");
	}

	// Mahsulotni yangilash (Admin va Super admin uchun)
	@Override
	@PreAuthorize("hasAnyRole('ADMIN','SUPER')")
	@PutMapping("/products")
	public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductKgObject product) {
		products.updateProduct(product);
		return ResponseEntity.ok("Mahsulot yangilandi");
	}

	// Do'kondagi barcha mahsulotlarni olish (Admin, Super admin va Ko'ruvchilar
	// uchun)
	@Override
	@PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
	@GetMapping("/stores/products")
	public ResponseEntity<List<ProductKgObject>> getAllProducts() {
		return ResponseEntity.ok(products.getAllProducts());
	}

	// Mahsulot zahirasini yangilash (Admin va Super admin uchun)
	@Override
	@PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
	@PutMapping("/stores/products/stock")
	public ResponseEntity<String> updateStock(@RequestParam String barcode, @RequestParam String newStock) {
		products.updateStock(barcode, newStock);
		return ResponseEntity.ok("Mahsulot miqdori yangilandi");
	}

}
