package com.SmartMarket.Control;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SmartMarket.ServiceLayer.ProductAnalsisService;

@RestController
@RequestMapping("/analysis")
public class AnalsisController implements AnalysisControlInterface {

	private final ProductAnalsisService service;

	public AnalsisController(ProductAnalsisService service) {
		this.service = service;
	}

	@Override
	@GetMapping("/product/theMost")
	public List<Object[]> getTheMostSaleProduct(
			@RequestParam int day,
			@RequestParam int limit) {
		return service.getTheMostSaleProduct(day, limit);
	}

	@Override
	@GetMapping("/product/theLeast")
	public List<Object[]> getTheLeastSaleProduct(
			@RequestParam int day,
			@RequestParam int limit) {
		return service.getTheLeastSaleProduct(day, limit);
	}

	@Override
	@GetMapping("/product/getsale/quantity")
	public List<Object[]> getSaleQuantityByHour(
			@RequestParam int day) {
		return service.getSaleQuantityByHour(day);
	}
}
