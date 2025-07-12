package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import com.SmartMarket.security.CustomUserDetailsService;
import com.SmartMarket.security.JwtTokenProvider;



/**
 * Asosiy biznes logikani bajaruvchi servis qatlami
 * Barcha ma'lumotlar bazasi operatsiyalari shu yerda amalga oshiriladi
 */
@Service
public class ServiceLayer implements ServiceLayerInterface {

    // Ma'lumotlar bazasi bilan ishlash uchun interfeyslar
    private final DALProductInterface products;  // Mahsulotlar uchun
    private final DALSalesInterface sale;       // Sotuvlar uchun
    private final DALStoresInterface store;     // Do'konlar uchun
    private final MonthInterface month;         // Oylik foydalar uchun
    
    // Autentifikatsiya komponentlari
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;  // Parollarni shifrlash uchun

    /**
     * Konstruktor - Dependency Injection orqali kerakli komponentlarni oladi
     */
    public ServiceLayer(DALProductInterface products, DALSalesInterface sale,
            DALStoresInterface store, MonthInterface month, AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            CustomUserDetailsService userDetailsService, ModelMapper modelMapper) {
    	this.modelMapper = modelMapper;
        this.products = products;
        this.sale = sale;
        this.store = store;
        this.month = month;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    /* ========= MAHSULOTLAR BO'LIMI ========= */
    
    /**
     * Yangi mahsulot qo'shish
     * @param product - qo'shiladigan mahsulot obyekti
     */
    @Override
    @Transactional
    public void addProduct(ProductsObject product) {
        products.save(product);
    }

    /**
     * ID bo'yicha mahsulot olish
     * @param storeId - do'kon IDsi
     * @param id - mahsulot IDsi
     * @return topilgan mahsulot yoki null
     */
    @Override
    @Transactional(readOnly = true)
    public ProductsObject getProduct(int storeId, int id) {
        return products.findByStoreIdAndBarcode(String.valueOf(storeId), String.valueOf(id));
    }

    /**
     * Mahsulotni o'chirish
     * @param product - o'chiriladigan mahsulot
     */
    @Override
    @Transactional
    public void deleteProduct(ProductsObject product) {
        products.delete(product);
    }

    /**
     * Mahsulot ma'lumotlarini yangilash
     * @param product - yangilanishi kerak bo'lgan mahsulot
     */
    @Override
    @Transactional
    public void updateProduct(ProductsObject product) {
        products.save(product);
    }

    /* ========= DO'KONLAR BO'LIMI ========= */
    
    /**
     * Do'kon parolini olish
     * @param store_id - do'kon IDsi
     * @return parol
     */
    @Override
    @Transactional(readOnly = true)
    public String getPasword(int store_id) {
        return store.getPasword(store_id);
    }

    /**
     * Do'kon ma'lumotlarini olish
     * @param store_id - do'kon IDsi
     * @return do'kon ma'lumotlari
     */
    @Override
    @Transactional(readOnly = true)
    public StoreDto getStore(int store_id) {
        Stores stor = store.findByStoreId(store_id);
        return modelMapper.map(stor, StoreDto.class);
    }

    /**
     * Do'kondagi barcha mahsulotlarni olish
     * @param id - do'kon IDsi
     * @return mahsulotlar ro'yxati
     */
    @Cacheable(value = "products", key = "#storeId")
    @Override
    @Transactional(readOnly = true)
    public List<ProductsObject> getAllProducts(int id) {
        return products.findByStoreId(String.valueOf(id));
    }

    /**
     * Do'kon parolini yangilash
     * @param store_id - do'kon IDsi
     * @param newPassword - yangi parol
     */
    @Override
    @Transactional
    public void updatePassword(int store_id, String newPassword) {
        // Mavjut dokon malumotlarini ol
        StoreDto st = getStore(store_id);
        
        // ModelMapper bilan DTO'dan Entity'e o'tish
        Stores s = modelMapper.map(st, Stores.class);
        
        // Sadece şifreyi güncelle (diğer alanlar korunur)
        s.setPassword(passwordEncoder.encode(newPassword));
        
        // Kaydet
        store.save(s);
    }

    /* ========= FOYDA (DAROMAD) BO'LIMI ========= */
    
    /**
     * Oylik foydani yangilash
     * @param storeId - do'kon IDsi
     * @param date - sana
     * @param eski - oldingi foyda
     * @param yangi - yangi foyda
     */
    @Override
    @Transactional
    public void updateMonthFoyda(int storeId, LocalDate date, long eski, long yangi) {
        long foyda = yangi + eski;
        month.updateMonthFoyda(storeId, date, foyda);
    }

    /**
     * Do'kon va oy bo'yicha foydani olish
     * @param storeId - do'kon IDsi
     * @param date - sana
     * @return foyda ro'yxati
     */
    @Override
    @Transactional(readOnly = true)
    public List<MonthFoyda> getMonthFoyda(int storeId, LocalDate date) {
        return month.findByStoreIdAndDateMonthYear(storeId, date);
    }

    /**
     * Mahsulot uchun oylik foydani yangilash
     * @param storeId - do'kon IDsi
     * @param productId - mahsulot IDsi
     * @param newValue - yangi foyda qiymati
     */
    @Override
    @Transactional
    public void updateProductMonthFoyda(int storeId, int productId, int newValue) {
        products.updateMonthFoyda(
            String.valueOf(storeId),
            String.valueOf(productId),
            Long.valueOf(newValue)
        );
    }

    /**
     * Mahsulot zahirasini yangilash
     * @param storeId - do'kon IDsi
     * @param barcode - mahsulot barkodi
     * @param newStock - yangi zahira miqdori
     * @return ta'sir o'tgan qatorlar soni
     */
    @Override
    @Transactional
    public int updateStock(String storeId, String barcode, String newStock) {
        return products.updateStock(storeId, barcode, newStock);
    }

    /**
     * Do'kon va oy bo'yicha umumiy foydani olish
     * @param storeId - do'kon IDsi
     * @param date - sana
     * @return foyda miqdori
     */
    @Override
    @Transactional
    public long foydaFindByStoreIdAndDateMonthYear(int storeId, LocalDate date) {
        return month.foydaFindByStoreIdAndDateMonthYear(storeId, date);
    }

    /* ========= SOTUVLAR BO'LIMI ========= */
    
    /**
     * Yangi sotuv qo'shish
     * @param s - sotuv ma'lumotlari
     */
    @Override
    @Transactional
    public void addSale(Sales s) {
        sale.save(s);
    }

    /**
     * ID bo'yicha sotuvni olish
     * @param id - sotuv IDsi
     * @return sotuv ma'lumotlari
     */
    @Override
    @Transactional(readOnly = true)
    public Sales getSale(int id) {
        return sale.getSale(id);
    }

    /**
     * Do'kon uchun barcha sotuvlarni olish
     * @param storeId - do'kon IDsi
     * @return sotuvlar ro'yxati
     */
    @Override
    @Transactional(readOnly = true)
    public List<Sales> getAllSale(int storeId) {
        return sale.getAllSale(storeId);
    }

    /**
     * Do'kon uchun bugungi sotuvlarni olish
     * @param storeId - do'kon IDsi
     * @param date - sana
     * @return sotuvlar ro'yxati
     */
    @Override
    @Transactional(readOnly = true)
    public List<Sales> getTodayAllSales(int storeId, LocalDate date) {
        LocalDate tomorow = date.plusDays(1);
        return sale.getTodayAllSales(storeId, date, tomorow);
    }

    /* ========= DO'KON YANGILASH ========= */
    
    /**
     * Do'kon ma'lumotlarini yangilash
     * @param dto - yangi ma'lumotlar
     */
    @Override
    @Transactional
    public void updateStore(StoreUpdateDto dto) {
        // Mevcut mağazayı bul
        Stores storeEntity = store.findByStoreId(dto.getStoreId());
        if (storeEntity == null) {
            throw new RuntimeException("Do'kon topilmadi");
        }
        
        // DTO'dan Entity'e güncelleme (password hariç)
        modelMapper.map(dto, storeEntity);
        
        // Özel password işleme
        if (StringUtils.hasText(dto.getPassword())) {
            storeEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
    }

    /* ========= AUTENTIFIKATSIYA ========= */
    
    /**
     * Tizimga kirish (login)
     * @param request - foydalanuvchi nomi va paroli
     * @return JWT token va boshqa ma'lumotlar
     */
    @Override
    @Transactional
    public AuthResponse login(AuthRequest request) {
        // Foydalanuvchi ma'lumotlarini tekshirish
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword())
        );

        // Foydalanuvchi ma'lumotlarini yuklash
        var userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // Foydalanuvchi rolini olish
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("ROLE_USER");

        // JWT token yaratish
        String token = jwtTokenProvider.createToken(userDetails.getUsername(), role);

        return new AuthResponse(token);
    }
}