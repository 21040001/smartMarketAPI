package com.SmartMarket.Control;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.dto.AuthRequest;
import com.SmartMarket.dto.AuthResponse;
import com.SmartMarket.dto.StoreDto;
import com.SmartMarket.dto.StoreUpdateDto;
import com.SmartMarket.ServiceLayer.MonthService;
import com.SmartMarket.ServiceLayer.ProductService;
import com.SmartMarket.ServiceLayer.SalesService;
import com.SmartMarket.ServiceLayer.ServiceLayer;
import com.SmartMarket.ServiceLayer.StoresService;

@RestController
@RequestMapping("/api")
public class Controller implements ControllerInterface {

    private final ServiceLayer service;
    private final ProductService products;
    private final MonthService month;
    private final SalesService sale;
    private final StoresService store;

   

    public Controller(ServiceLayer service, ProductService product, MonthService month, SalesService sale,
			StoresService store) {
		super();
		this.service = service;
		this.products = product;
		this.month = month;
		this.sale = sale;
		this.store = store;
	}

	/* Mahsulotlar bilan ishlash */
    // Yangi mahsulot qo'shish (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductsObject product) {
        products.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mahsulot muvaffaqiyatli qo'shildi");
    }

    // Mahsulotni olish (Admin, Super admin va Ko'ruvchilar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductsObject> getProduct(@PathVariable("id") int id) {
        ProductsObject product = products.getProduct(id);
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
    public ResponseEntity<String> deleteProduct(@Valid @RequestBody ProductsObject product) {
        products.deleteProduct(product);
        return ResponseEntity.ok("Mahsulot o'chirildi");
    }

    // Mahsulotni yangilash (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @PutMapping("/products")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductsObject product) {
        products.updateProduct(product);
        return ResponseEntity.ok("Mahsulot yangilandi");
    }

    /* Do'konlar bilan ishlash */

    // Do'kon ma'lumotlarini olish (Admin, Super admin va Ko'ruvchilar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/stores")
    public ResponseEntity<StoreDto> getStore() {
        return ResponseEntity.ok(store.getStore());
    }

    // Do'kondagi barcha mahsulotlarni olish (Admin, Super admin va Ko'ruvchilar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/stores/products")
    public ResponseEntity<List<ProductsObject>> getAllProducts() {
        return ResponseEntity.ok(products.getAllProducts());
    }

    // Do'kon parolini yangilash (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @PutMapping("/stores/password")
    public ResponseEntity<String> updatePassword(
            @RequestParam String newPassword) {
        store.updatePassword( newPassword);
        return ResponseEntity.ok("Parol yangilandi");
    }

    /* Foyda (daromad) bilan ishlash */

    // Oylik foydani yangilash (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER', 'CASHIER')")
    @PutMapping("/stores/month-foyda")
    public ResponseEntity<String> updateMonthFoyda(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam long eski,
            @RequestParam long yangi) {
        month.updateMonthFoyda( date, eski, yangi);
        return ResponseEntity.ok("Oy foydasi yangilandi");
    }

    // Oylik foydani olish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/stores/month-foyda")
    public ResponseEntity<List<MonthFoyda>> getMonthFoyda(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(month.getMonthFoyda(date));
    }

    // Mahsulot foydasini yangilash (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @PutMapping("/stores/products/{productId}/foyda")
    public ResponseEntity<String> updateProductMonthFoyda(
            @PathVariable int productId,
            @RequestParam int newValue) {
        products.updateProductMonthFoyda( productId, newValue);
        return ResponseEntity.ok("Mahsulot foydasi yangilandi");
    }

    /* Sotuvlar bilan ishlash */

    // Mahsulot zahirasini yangilash (Admin va Super admin uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @PutMapping("/stores/products/stock")
    public ResponseEntity<String> updateStock(
            @RequestParam String barcode,
            @RequestParam String newStock) {
    	products.updateStock( barcode, newStock);
        return ResponseEntity.ok("Mahsulot miqdori yangilandi");
    }

    // Do'kon foydasini olish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/stores/foyda")
    public ResponseEntity<Long> foydaFindByStoreIdAndDateMonthYear(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(month.foydaFindByStoreIdAndDateMonthYear( date));
    }

    // Yangi sotuv qo'shish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @PostMapping("/sales")
    public ResponseEntity<String> addSale(@Valid @RequestBody Sales s) {
        sale.addSale(s);
        return ResponseEntity.ok("Muvaffaqiyatli qo'shildi");
    }

    // Sotuvni olish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/sales/{id}")
    public ResponseEntity<Sales> getSale(@PathVariable int id) {
        return ResponseEntity.ok(sale.getSale(id));
    }

    // Barcha sotuvlarni olish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/stores/sales")
    public ResponseEntity<List<Sales>> getAllSale() {
        return ResponseEntity.ok(sale.getAllSale());
    }

    // Bugungi sotuvlarni olish (Admin, Super admin va Kassirlar uchun)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/stores/sales/today")
    public ResponseEntity<List<Sales>> getTodayAllSales(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(sale.getTodayAllSales( date));
    }

    // Do'kon ma'lumotlarini yangilash (Super uchun)
    @PreAuthorize("hasAnyRole('SUPER')")
    @PutMapping("/stores")
    public ResponseEntity<String> updateStore(@Valid @RequestBody StoreUpdateDto stores) {
        store.updateStore(stores);
        return ResponseEntity.ok("Do'kon ma'lumotlari muvaffaqiyatli yangilandi.");
    }
    
    /* Autentifikatsiya */

    // Tizimga kirish (barcha foydalanuvchilar uchun ochiq)
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }
}