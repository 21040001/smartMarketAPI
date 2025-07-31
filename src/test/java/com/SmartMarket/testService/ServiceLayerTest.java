package com.SmartMarket.testService;

import com.SmartMarket.dto.*;
import com.SmartMarket.Entity.*;
import com.SmartMarket.HibernateDAL.*;
import com.SmartMarket.ServiceLayer.MonthServiceInterfcae;
import com.SmartMarket.ServiceLayer.ProductServiceInterface;
import com.SmartMarket.ServiceLayer.ServiceLayerInterface;
import com.SmartMarket.ServiceLayer.StoresServiceInterface;
import com.SmartMarket.security.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
public class ServiceLayerTest {

    
    @Autowired
    private MonthServiceInterfcae month;
    @Autowired
    private StoresServiceInterface store;
    @Autowired
    private ProductServiceInterface products;

    @MockBean private DALProductInterface productRepo;
    @MockBean private DALSalesInterface saleRepo;
    @MockBean private DALStoresInterface storeRepo;
    @MockBean private MonthInterface monthRepo;
    @MockBean private AuthenticationManager authenticationManager;
    @MockBean private JwtTokenProvider jwtTokenProvider;
    @MockBean private CustomUserDetailsService userDetailsService;
    @MockBean private ModelMapper modelMapper;
    @MockBean private PasswordEncoder passwordEncoder;

    // getProduct - ürün varsa
    @Test
    void test_getProduct_shouldReturnProduct_whenFound() {
        ProductsObject product = new ProductsObject();
        product.setStoreId("123");
        product.setBarcode("456");

        Mockito.when(productRepo.findByStoreIdAndBarcode("123", "456"))
               .thenReturn(Optional.of(product));

        ProductsObject result = products.getProduct(256);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("123", result.getStoreId());
        Assertions.assertEquals("456", result.getBarcode());
    }

    // getProduct - ürün yoksa
    @Test
    void test_getProduct_shouldThrowException_whenNotFound() {
        Mockito.when(productRepo.findByStoreIdAndBarcode("123", "999"))
               .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            products.getProduct(256);
        });
    }

    // updatePassword testi
    @Test
    void test_updatePassword_shouldEncodeAndSavePassword() {
        // Arrange
        StoreDto dto = new StoreDto();
        dto.setStoreId(123);

        Stores storeEntity = new Stores();
        storeEntity.setStoreId(123);

        // Mock: getStore() içinde çağrılan repository
        Mockito.when(storeRepo.findByStoreId(123)).thenReturn(Optional.of(storeEntity));

        // DTO'dan Entity'ye dönüşüm
        Mockito.when(modelMapper.map(dto, Stores.class)).thenReturn(storeEntity);

        // Password encode
        Mockito.when(passwordEncoder.encode("newpass")).thenReturn("encodedpass");

        // Mocklama yapılmazsa getStore() hata verir
        Mockito.when(modelMapper.map(storeEntity, StoreDto.class)).thenReturn(dto);

        // Act
        store.updatePassword( "newpass");

        // Assert
        Mockito.verify(storeRepo).save(Mockito.argThat(s ->
            s.getPassword().equals("encodedpass")
        ));
    }


    // foydaFindByStoreIdAndDateMonthYear - varsa
    @Test
    void test_foydaFindByStoreIdAndDateMonthYear_shouldReturnValue_whenFound() {
        Mockito.when(monthRepo.foydaFindByStoreIdAndDateMonthYear(1, LocalDate.of(2025, 7, 1)))
               .thenReturn(Optional.of(5000L));

        long result = month.foydaFindByStoreIdAndDateMonthYear( LocalDate.of(2025, 7, 1));

        Assertions.assertEquals(5000L, result);
    }

    // foydaFindByStoreIdAndDateMonthYear - yoksa
    @Test
    void test_foydaFindByStoreIdAndDateMonthYear_shouldThrow_whenNotFound() {
        Mockito.when(monthRepo.foydaFindByStoreIdAndDateMonthYear(1, LocalDate.of(2025, 7, 1)))
               .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            month.foydaFindByStoreIdAndDateMonthYear( LocalDate.of(2025, 7, 1));
        });
    }

    

    // getPasword - varsa
    @Test
    void test_getPassword_shouldReturn_whenFound() {
        Mockito.when(storeRepo.getPasword(1)).thenReturn(Optional.of("1234"));

        String password = store.getPasword();

        Assertions.assertEquals("1234", password);
    }

    // getPasword - yoksa
    @Test
    void test_getPassword_shouldThrow_whenNotFound() {
        Mockito.when(storeRepo.getPasword(99)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> {
            store.getPasword();
        });
    }

    // getStore testi
    @Test
    void test_getStore_shouldMapAndReturnDto() {
        Stores s = new Stores();
        StoreDto dto = new StoreDto();
        dto.setStoreId(1);

        Mockito.when(storeRepo.findByStoreId(1)).thenReturn(Optional.of(s));
        Mockito.when(modelMapper.map(s, StoreDto.class)).thenReturn(dto);

        StoreDto result = store.getStore();

        Assertions.assertEquals(1, result.getStoreId());
    }
}
