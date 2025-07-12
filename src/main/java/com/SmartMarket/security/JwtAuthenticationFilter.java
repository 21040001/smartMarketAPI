package com.SmartMarket.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import java.io.IOException;

/**
 * JWT asosida foydalanuvchi autentifikatsiyasi uchun filter
 * Har bir kiruvchi HTTP so'rovni tekshiradi va JWT tokenini validatsiya qiladi
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    private final JwtTokenProvider jwtTokenProvider; // JWT tokenlarini boshqaruvchi
    private final CustomUserDetailsService userDetailsService; // Foydalanuvchi ma'lumotlari servisi

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, 
                                 CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Har bir so'rovni filtrlash logikasi
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            // 1. So'rovdan JWT tokenini olish
            String jwt = getJwtFromRequest(request);
            
            // 2. Token mavjud va yaroqli bo'lsa
            if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
                // 3. Tokendan foydalanuvchi nomini olish
                String username = jwtTokenProvider.getUsername(jwt);
                
                // 4. Foydalanuvchi ma'lumotlarini yuklash
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // 5. Autentifikatsiya obyektini yaratish
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities());
                
                // 6. So'rov tafsilotlarini qo'shish
                authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 7. SecurityContextga autentifikatsiyani o'rnatish
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                logger.debug("Autentifikatsiya qilingan foydalanuvchi: {}", username);
            }
        } catch (ExpiredJwtException ex) {
            // Token muddati tugagan
            logger.error("JWT token muddati tugagan: {}", ex.getMessage());
            sendErrorResponse(response, "Token muddati tugagan", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (JwtException | IllegalArgumentException ex) {
            // Yaroqsiz token
            logger.error("JWT validatsiya xatosi: {}", ex.getMessage());
            sendErrorResponse(response, "Yaroqsiz token", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception ex) {
            // Boshqa xatolar
            logger.error("Autentifikatsiya xatosi", ex);
            sendErrorResponse(response, "Autentifikatsiya muvaffaqiyatsiz", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        // 8. Filter zanjirini davom ettirish
        filterChain.doFilter(request, response);
    }

    /**
     * HTTP so'rovdan JWT tokenini olish
     * Format: "Bearer <token>"
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " dan keyingi qismini olish
        }
        return null;
    }

    /**
     * Xato javobini yuborish
     */
    private void sendErrorResponse(HttpServletResponse response, 
                                 String message, 
                                 int status) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(
            String.format("{\"status\": %d, \"xato\": \"%s\"}", status, message));
    }

    /**
     * Qaysi so'rovlar filtrdan o'tkazilmasligini belgilash
     * Kirish va Swagger endpointlari filtrlanmaydi
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/") || path.startsWith("/swagger-ui/");
    }
}