package com.SmartMarket.security;

import com.SmartMarket.Entity.Users;
import com.SmartMarket.HibernateDAL.UserDAL;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Ma'lumotlar bazasidan foydalanuvchi ma'lumotlarini olish uchun repository
    private final UserDAL userRepository;

    // Konstruktor orqali dependency injection
    public CustomUserDetailsService(UserDAL repo) {
        this.userRepository = repo;
    }

    /**
     * Foydalanuvchi nomi bo'yicha foydalanuvchi ma'lumotlarini yuklash
     * @param username - tizimga kirish uchun ishlatiladigan foydalanuvchi nomi
     * @return UserDetails - Spring Security uchun foydalanuvchi ma'lumotlari
     * @throws UsernameNotFoundException - Agar foydalanuvchi topilmasa
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ma'lumotlar bazasidan foydalanuvchini qidirish
        Users user = userRepository.findByUsername(username)
                   .orElseThrow(() -> new UsernameNotFoundException("Foydalanuvchi topilmadi: " + username));

        // Spring Security uchun UserDetails obyektini yaratish
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),          // Foydalanuvchi nomi
            user.getPassword(),         // Shifrlangan parol
            List.of(new SimpleGrantedAuthority(user.getRole()))  // Foydalanuvchi roli
        );
    }
    
    public int getStoreIdByUsername(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Foydalanuvchi topilmadi: " + username));
        return user.getStore_id();
    }
}