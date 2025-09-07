package com.SmartMarket.control;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.SmartMarket.entity.Payment;
import com.SmartMarket.serviceLayer.PaymentsServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/payment")
public class PaymentController implements PaymentControllerInterface {

   
    private PaymentsServiceInterface service;

    
    public PaymentController(PaymentsServiceInterface service) {
		super();
		this.service = service;
	}

	// 🔹 Müşterinin son ödemesini getir
    @GetMapping("/last/{customerId}")
    public ResponseEntity<Payment> getCustomerLastPayment(@PathVariable int customerId) {
        Payment p = service.getCustomerLastPayment(customerId);
        if (p != null) {
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔹 Müşterinin tüm ödemeleri
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/customer/all")
    public ResponseEntity<List<Payment>> getCustomerPayments(@RequestParam int customerId) {
        List<Payment> p = service.getCustomerPayments(customerId);
        return ResponseEntity.ok(p);
    }

    // 🔹 Mağazadaki tüm ödemeler (tarih aralığı)
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getStorePayments(@RequestParam int start, @RequestParam int end) {
        List<Payment> p = service.getStorePayments(start, end);
        return ResponseEntity.ok(p);
    }

    // 🔹 Türüne göre ödemeler
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/type/all")
    public ResponseEntity<List<Payment>> getPaymentsByType(@RequestParam String type,
                                                           @RequestParam int start,
                                                           @RequestParam int end) {
        List<Payment> p = service.getPaymentsByType(type, start, end);
        return ResponseEntity.ok(p);
    }

    // 🔹 Müşterinin toplam ödemesi
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @GetMapping("/customer/total")
    public ResponseEntity<Double> getCustomerTotalPayment(@RequestParam int customerId) {
        double p = service.getCustomerTotalPayment(customerId);
        return ResponseEntity.ok(p);
    }

    // 🔹 Yeni ödeme ekleme
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @PostMapping("/add")
    public ResponseEntity<String> addPayment(@Valid @RequestBody Payment p) {
        service.addPayment(p);
        return ResponseEntity.ok("Fatura qo'shildi");
    }

    // 🔹 Ödemeyi tamamlama
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','CASHIER')")
    @PutMapping("/pay/done")
    public ResponseEntity<String> donePay(@RequestParam Long paymentId,
                                          @RequestParam Double amount,
                                          @RequestParam String method) {
        service.donePay(paymentId, amount, method);
        return ResponseEntity.ok("To'lo'v bajarildi.");
    }
}
