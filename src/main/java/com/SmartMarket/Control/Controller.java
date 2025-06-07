package com.SmartMarket.Control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.Entity.SalesObject;
import com.SmartMarket.Entity.Stores;
import com.SmartMarket.ServiceLayer.ServiceLayer;

@RestController
@RequestMapping("/")
@CrossOrigin
public class Controller implements ControllerInterface{

	private ServiceLayer service;
	
	public Controller(ServiceLayer service) {
		super();
		this.service = service;
	}

	@Override
	@PostMapping("/add")
	public void addProduct(@RequestBody ProductsObject product) {
		service.addProduct(product);
		
	}

	@Override
	@GetMapping("/product/{id}")
	public ProductsObject getProduct(@PathVariable("id") int id) {
		// TODO Auto-generated method stub
		return service.getProduct(id);
	}

	@Override
	@PostMapping("/delete")
	public void deleteProduct(@RequestBody ProductsObject product) {
		service.deleteProduct(product);
		
	}

	@Override
	@PostMapping("/update")
	public void updateProduct(@RequestBody ProductsObject product) {
		service.updateProduct(product);
	}

	
	@Override
	@GetMapping("/logIn/{id}")
	public String getPasword(@PathVariable("id") int store_id) {
		// TODO Auto-generated method stub
		return service.getPasword(store_id);
	}

	@Override
	@GetMapping("/store/{id}")
	public Stores getStore(@PathVariable("id") int store_id) {
		// TODO Auto-generated method stub
		return service.getStore(store_id);
	}

	@Override
	@GetMapping("/products/{id}")
	public List<ProductsObject> getAllProducts(@PathVariable("id") int id) {
		// TODO Auto-generated method stub
		return service.getAllProducts(id);
	}

	@PostMapping("/addSales")
	public void addSales(@RequestBody SalesObject salesObject) {
	    Sales sales = new Sales();
	    sales.setStoreId(salesObject.getStoreId());
	    sales.setSaleDate(salesObject.getSaleDate());
	    sales.setTotalPrice(salesObject.getTotalPrice());

	    service.addSales(sales);
	    }

	
	@GetMapping("/sales/today")
	public List<Sales> getTodaySales(
	    @RequestParam int storeId,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate todayDate,
	    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tomorrowDate) {
	    
	    if (tomorrowDate == null) {
	        tomorrowDate = todayDate.plusDays(1);
	    }
	    
	    return service.getTodaySales(storeId, todayDate, tomorrowDate);
	}

	@Override
	@GetMapping("/update/password")
	public void updatePassword(
			@RequestParam int store_id, 
			@RequestParam String newPassword) {
		service.updatePassword(store_id, newPassword);
	}
	}



