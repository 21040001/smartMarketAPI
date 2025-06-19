package com.SmartMarket.Control;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.Entity.SalesObject;
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

    @PostMapping("/addSales")
    public ResponseEntity<String> addSales(@RequestBody SalesObject salesObject) {
        Sales sales = new Sales();
        sales.setStoreId(salesObject.getStoreId());
        sales.setSaleDate(salesObject.getSaleDate());
        sales.setTotalPrice(salesObject.getTotalPrice());

        service.addSales(sales);
        return ResponseEntity.status(HttpStatus.CREATED).body("Savdo muvaffaqiyatli qo‘shildi");
    }

    @GetMapping("/sales/today")
    public ResponseEntity<List<Sales>> getTodaySales(
            @RequestParam int storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate todayDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tomorrowDate) {

        if (tomorrowDate == null) {
            tomorrowDate = todayDate.plusDays(1);
        }

        return ResponseEntity.ok(service.getTodaySales(storeId, todayDate, tomorrowDate));
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
}
