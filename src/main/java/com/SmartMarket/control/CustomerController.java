package com.SmartMarket.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SmartMarket.entity.Customer;
import com.SmartMarket.serviceLayer.CustomerServiceInterface;

@RestController
@RequestMapping("/api/customers")
public class CustomerController implements CustomerControllerInterface {

    @Autowired
    private CustomerServiceInterface service;

    // Barcha mijozlarni olish
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customers = service.getAllCustomer();
        return ResponseEntity.ok(customers);
    }

    // Yangi mijoz qo'shish
    @PostMapping
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        service.addCustomer(customer);
        return ResponseEntity.ok("Yangi xaridor muvaffaqiyatli qo'shildi.");
    }

    // Mijozni yangilash
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setCustomerId(id); // ID ni majburan set qilish
        service.updateCustomer(customer);
        return ResponseEntity.ok("Xaridor ma'lumotlari muvaffaqiyatli yangilandi.");
    }

    // Mijozni o'chirish
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        Customer customer = service.getCustomer(id.intValue());
        if (customer != null) {
            service.deleteCustomer(customer);
            return ResponseEntity.ok("Xaridor muvaffaqiyatli o'chirildi.");
        }
        return ResponseEntity.notFound().build();
    }

    // Bitta mijozni olish
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable long id) {
        Customer customer = service.getCustomer(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }

    // Mijoz qarz qo'shish
    @PostMapping("/{id}/debt/add")
    public ResponseEntity<String> addDebt(@PathVariable Long id, @RequestParam double newBalance) {
        service.addDebt(id, newBalance);
        return ResponseEntity.ok(id + " li xaridor hisobiga " + newBalance + " qarz qo'shildi.");
    }

    // Mijoz qarzini o'chirish
    @PostMapping("/{id}/debt/delete")
    public ResponseEntity<String> deleteDebt(@PathVariable Long id, @RequestParam double newBalance) {
        service.deleteDebt(id, newBalance);
        return ResponseEntity.ok("Xaridor qarzi o'chirildi.");
    }

	
}
