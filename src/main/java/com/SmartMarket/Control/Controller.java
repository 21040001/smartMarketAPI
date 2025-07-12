package com.SmartMarket.Control;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.dto.AuthRequest;
import com.SmartMarket.dto.AuthResponse;
import com.SmartMarket.dto.StoreDto;
import com.SmartMarket.dto.StoreUpdateDto;
import com.SmartMarket.ServiceLayer.ServiceLayer;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Barcha manbalardan so'rovlarni qabul qilish (maxsus manzillarni ham belgilash mumkin)
public class Controller implements ControllerInterface {

    private final ServiceLayer service;

    public Controller(ServiceLayer service) {
        this.service = service;
    }

    /* Mahsulotlar bilan ishlash */

    // Yangi mahsulot qo'shish (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody ProductsObject product) {
        service.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mahsulot muvaffaqiyatli qo'shildi");
    }

    // Mahsulotni olish (Admin, Super admin va Ko'ruvchilar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','VIEWER')")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductsObject> getProduct(
            @RequestParam int storeId,
            @PathVariable("id") int id) {
        ProductsObject product = service.getProduct(storeId, id);
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
    public ResponseEntity<String> deleteProduct(@RequestBody ProductsObject product) {
        service.deleteProduct(product);
        return ResponseEntity.ok("Mahsulot o'chirildi");
    }

    // Mahsulotni yangilash (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @PutMapping("/products")
    public ResponseEntity<String> updateProduct(@RequestBody ProductsObject product) {
        service.updateProduct(product);
        return ResponseEntity.ok("Mahsulot yangilandi");
    }

    /* Do'konlar bilan ishlash */

    // Do'kon ma'lumotlarini olish (Admin, Super admin va Ko'ruvchilar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','VIEWER')")
    @GetMapping("/stores/{id}")
    public ResponseEntity<StoreDto> getStore(@PathVariable("id") int store_id) {
        return ResponseEntity.ok(service.getStore(store_id));
    }

    // Do'kondagi barcha mahsulotlarni olish (Admin, Super admin va Ko'ruvchilar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','VIEWER')")
    @GetMapping("/stores/{id}/products")
    public ResponseEntity<List<ProductsObject>> getAllProducts(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.getAllProducts(id));
    }

    // Do'kon parolini yangilash (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @PutMapping("/stores/{storeId}/password")
    public ResponseEntity<String> updatePassword(
            @PathVariable int storeId,
            @RequestParam String newPassword) {
        service.updatePassword(storeId, newPassword);
        return ResponseEntity.ok("Parol yangilandi");
    }

    /* Foyda (daromad) bilan ishlash */

    // Oylik foydani yangilash (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @PutMapping("/stores/{storeId}/month-foyda")
    public ResponseEntity<String> updateMonthFoyda(
            @PathVariable int storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam long eski,
            @RequestParam long yangi) {
        service.updateMonthFoyda(storeId, date, eski, yangi);
        return ResponseEntity.ok("Oy foydasi yangilandi");
    }

    // Oylik foydani olish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/stores/{storeId}/month-foyda")
    public ResponseEntity<List<MonthFoyda>> getMonthFoyda(
            @PathVariable int storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.getMonthFoyda(storeId, date));
    }

    // Mahsulot foydasini yangilash (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @PutMapping("/stores/{storeId}/products/{productId}/foyda")
    public ResponseEntity<String> updateProductMonthFoyda(
            @PathVariable int storeId,
            @PathVariable int productId,
            @RequestParam int newValue) {
        service.updateProductMonthFoyda(storeId, productId, newValue);
        return ResponseEntity.ok("Mahsulot foydasi yangilandi");
    }

    /* Sotuvlar bilan ishlash */

    // Mahsulot zahirasini yangilash (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @PutMapping("/stores/{storeId}/products/stock")
    public ResponseEntity<Integer> updateStock(
            @PathVariable String storeId,
            @RequestParam String barcode,
            @RequestParam String newStock) {
        return ResponseEntity.ok(service.updateStock(storeId, barcode, newStock));
    }

    // Do'kon foydasini olish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/stores/{storeId}/foyda")
    public ResponseEntity<Long> foydaFindByStoreIdAndDateMonthYear(
            @PathVariable int storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.foydaFindByStoreIdAndDateMonthYear(storeId, date));
    }

    // Yangi sotuv qo'shish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @PostMapping("/sales")
    public ResponseEntity<String> addSale(@RequestBody Sales s) {
        service.addSale(s);
        return ResponseEntity.ok("Muvaffaqiyatli qo'shildi");
    }

    // Sotuvni olish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/sales/{id}")
    public ResponseEntity<Sales> getSale(@PathVariable int id) {
        return ResponseEntity.ok(service.getSale(id));
    }

    // Barcha sotuvlarni olish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/stores/{storeId}/sales")
    public ResponseEntity<List<Sales>> getAllSale(@PathVariable int storeId) {
        return ResponseEntity.ok(service.getAllSale(storeId));
    }

    // Bugungi sotuvlarni olish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/stores/{storeId}/sales/today")
    public ResponseEntity<List<Sales>> getTodayAllSales(
            @PathVariable int storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.getTodayAllSales(storeId, date));
    }

    // Do'kon ma'lumotlarini yangilash (Admin va Super admin uchun)
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @PutMapping("/stores")
    public ResponseEntity<String> updateStore(@RequestBody StoreUpdateDto store) {
        service.updateStore(store);
        return ResponseEntity.ok("Do'kon muvaffaqiyatli yangilandi");
    }
    
    /* Autentifikatsiya */

    // Tizimga kirish (barcha foydalanuvchilar uchun ochiq)
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }
}