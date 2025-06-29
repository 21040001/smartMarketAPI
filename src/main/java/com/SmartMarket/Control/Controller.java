package com.SmartMarket.Control;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.Entity.Stores;
import com.SmartMarket.ServiceLayer.ServiceLayer;

@RestController
@RequestMapping("/")
@CrossOrigin
public class Controller implements ControllerInterface {

    private final ServiceLayer service;

    public Controller(ServiceLayer service) {
        this.service = service;
    }

    @Override
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductsObject product) {
        service.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mahsulot muvaffaqiyatli qo‘shildi");
    }

    @Override
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductsObject> getProduct(@RequestParam int storeId, @PathVariable("id") int id) {
        ProductsObject product = service.getProduct(storeId, id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    @PostMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestBody ProductsObject product) {
        service.deleteProduct(product);
        return ResponseEntity.ok("Mahsulot o‘chirildi");
    }

    @Override
    @PostMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductsObject product) {
        service.updateProduct(product);
        return ResponseEntity.ok("Mahsulot yangilandi");
    }

    @Override
    @GetMapping("/logIn/{id}")
    public ResponseEntity<String> getPasword(@PathVariable("id") int store_id) {
        return ResponseEntity.ok(service.getPasword(store_id));
    }

    @Override
    @GetMapping("/store/{id}")
    public ResponseEntity<Stores> getStore(@PathVariable("id") int store_id) {
        return ResponseEntity.ok(service.getStore(store_id));
    }

    @Override
    @GetMapping("/products/{id}")
    public ResponseEntity<List<ProductsObject>> getAllProducts(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.getAllProducts(id));
    }


    @Override
    @GetMapping("/update/password")
    public ResponseEntity<String> updatePassword(
            @RequestParam int store_id,
            @RequestParam String newPassword) {
        service.updatePassword(store_id, newPassword);
        return ResponseEntity.ok("Parol yangilandi");
    }

    @Override
    @GetMapping("/update/month")
    public ResponseEntity<String> updateMonthFoyda(
            @RequestParam int storeId,
            @RequestParam LocalDate date,
            @RequestParam long eski,
            @RequestParam long yeni) {
        service.updateMonthFoyda(storeId, date, eski, yeni);
        return ResponseEntity.ok("Oy foydasi yangilandi");
    }

    @Override
    @GetMapping("/get/month")
    public ResponseEntity<List<MonthFoyda>> getMonthFoyda(
            @RequestParam int storeId,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(service.getMonthFoyda(storeId, date));
    }

    @Override
    @GetMapping("/update/product/foyda")
    public ResponseEntity<String> updateProductMonthFoyda(
            @RequestParam int storeId, @RequestParam int productId, @RequestParam int newValue) {
        service.updateProductMonthFoyda(storeId, productId, newValue);
        return ResponseEntity.ok("Mahsulot foydasi yangilandi");
    }

	@Override
	@GetMapping("/update/product/stock")
	public ResponseEntity<Integer> updateStock(
			@RequestParam String storeId, 
			@RequestParam String barcode, 
			@RequestParam String newStock) {
		return ResponseEntity.ok(service.updateStock(storeId, barcode, newStock));
	}

	@Override
	@GetMapping("/get/day")
	public ResponseEntity<Long> foydaFindByStoreIdAndDateMonthYear(
			@RequestParam int storeId, 
			@RequestParam LocalDate date) {
		return ResponseEntity.ok(service.foydaFindByStoreIdAndDateMonthYear(storeId, date));
	}

	@Override
	@PostMapping("/add/sale")
	public ResponseEntity<String> addSale(@RequestBody Sales s) {
		service.addSale(s);
		return ResponseEntity.ok("Succesfully");
	}

	@Override
	@GetMapping("/get/sale")
	public ResponseEntity<Sales> getSale(@RequestParam int id) {
		return ResponseEntity.ok(service.getSale(id));
	}

	@Override
	@GetMapping("/get/allsale")
	public ResponseEntity<List<Sales>> getAllSale(@RequestParam int storeId) {
		return ResponseEntity.ok(service.getAllSale(storeId));
	}

	@Override
	@GetMapping("/get/allsale/today")
	public ResponseEntity<List<Sales>> getTodayAllSales(@RequestParam int storeId, @RequestParam LocalDate date) {
		return ResponseEntity.ok(service.getTodayAllSales(storeId, date));
	}

	@PostMapping("/update/store")
	public ResponseEntity<String> updateStore(@RequestBody Stores store) {
	    service.updateStore(store);
	    return ResponseEntity.ok("Store updated successfully");
	}

}
