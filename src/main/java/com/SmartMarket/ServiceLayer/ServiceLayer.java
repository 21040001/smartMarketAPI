package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;
import com.SmartMarket.Entity.Stores;
import com.SmartMarket.HibernateDAL.*;
import com.SmartMarket.dto.AuthRequest;
import com.SmartMarket.dto.AuthResponse;
import com.SmartMarket.dto.StoreDto;
import com.SmartMarket.dto.StoreUpdateDto;
import com.SmartMarket.exceptions.*;
import com.SmartMarket.security.CustomUserDetailsService;
import com.SmartMarket.security.JwtTokenProvider;
import com.SmartMarket.dto.StoreIdDto;

@Service
public class ServiceLayer implements ServiceLayerInterface {

    private final DALProductInterface products;
    private final DALSalesInterface sale;
    private final DALStoresInterface store;
    private final MonthInterface month;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ServiceLayer(DALProductInterface products, DALSalesInterface sale,
                        DALStoresInterface store, MonthInterface month,
                        AuthenticationManager authenticationManager,
                        JwtTokenProvider jwtTokenProvider,
                        CustomUserDetailsService userDetailsService,
                        ModelMapper modelMapper) {
        this.products = products;
        this.sale = sale;
        this.store = store;
        this.month = month;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
    }

    // 🔐 Kullanıcıdan storeId'yi token'dan al
    private int getCurrentStoreId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
        return principal.getStoreId();
    }

    // ========== Mahsulotlar ==========

    @Override
    @Transactional
    public void addProduct(ProductsObject product) {
        products.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductsObject getProduct(int id) {
        int storeId = getCurrentStoreId();
        return products.findByStoreIdAndBarcode(String.valueOf(storeId), String.valueOf(id))
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteProduct(ProductsObject product) {
        products.delete(product);
    }

    @Override
    @Transactional
    public void updateProduct(ProductsObject product) {
        products.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "products", key = "#root.methodName")
    public List<ProductsObject> getAllProducts() {
        int storeId = getCurrentStoreId();
        return products.findByStoreId(String.valueOf(storeId));
    }

    // ========== Do'konlar ==========

    @Override
    @Transactional(readOnly = true)
    public String getPasword() {
        int storeId = getCurrentStoreId();
        return store.getPasword(storeId)
                .orElseThrow(() -> new CannotGetPassword(String.valueOf(storeId)));
    }

    @Override
    @Transactional(readOnly = true)
    public StoreDto getStore() {
        int storeId = getCurrentStoreId();
        return store.findByStoreId(storeId)
                .map(s -> modelMapper.map(s, StoreDto.class))
                .orElseThrow(() -> new StoreNotFoundException(String.valueOf(storeId)));
    }

    @Override
    @Transactional
    public void updatePassword(String newPassword) {
        int storeId = getCurrentStoreId();
        StoreDto dto = getStore();
        Stores s = modelMapper.map(dto, Stores.class);
        s.setPassword(passwordEncoder.encode(newPassword));
        store.save(s);
    }

    @Override
    @Transactional
    public void updateStore(StoreUpdateDto dto) {
        Stores storeEntity = store.findByStoreId(dto.getStoreId())
                .orElseThrow(() -> new StoreNotFoundException(String.valueOf(dto.getStoreId())));
        modelMapper.map(dto, storeEntity);
        if (StringUtils.hasText(dto.getPassword())) {
            storeEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
    }

    // ========== Foyda ==========

    @Override
    @Transactional
    public void updateMonthFoyda(LocalDate date, long eski, long yangi) {
        int storeId = getCurrentStoreId();
        month.updateMonthFoyda(storeId, date, eski + yangi);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonthFoyda> getMonthFoyda(LocalDate date) {
        int storeId = getCurrentStoreId();
        return month.findByStoreIdAndDateMonthYear(storeId, date);
    }

    @Override
    @Transactional
    public void updateProductMonthFoyda(int productId, int newValue) {
        int storeId = getCurrentStoreId();
        products.updateMonthFoyda(String.valueOf(storeId), String.valueOf(productId), (long) newValue);
    }

    @Override
    @Transactional
    public void updateStock(String barcode, String newStock) {
        int storeId = getCurrentStoreId();
        products.updateStock(String.valueOf(storeId), barcode, newStock);
    }

    @Override
    @Transactional
    public long foydaFindByStoreIdAndDateMonthYear(LocalDate date) {
        int storeId = getCurrentStoreId();
        return month.foydaFindByStoreIdAndDateMonthYear(storeId, date)
                .orElseThrow(() -> new MotnhFoydaNotFound(date));
    }

    // ========== Sotuvlar ==========

    @Override
    @Transactional
    public void addSale(Sales s) {
        sale.save(s);
    }

    @Override
    @Transactional(readOnly = true)
    public Sales getSale(int id) {
        return sale.getSale(id)
                .orElseThrow(() -> new SaleNotFoundException(String.valueOf(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sales> getAllSale() {
        int storeId = getCurrentStoreId();
        return sale.getAllSale(storeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sales> getTodayAllSales(LocalDate date) {
        int storeId = getCurrentStoreId();
        return sale.getTodayAllSales(storeId, date, date.plusDays(1));
    }

    // ========== Auth ==========

    @Override
    @Transactional
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String role = userDetails.getAuthorities().stream()
                .findFirst().map(a -> a.getAuthority())
                .orElse("ROLE_USER");

        int storeId = userDetailsService.getStoreIdByUsername(request.getUsername());
        String token = jwtTokenProvider.createToken(userDetails.getUsername(), role, storeId);

        return new AuthResponse(token);
    }
}
