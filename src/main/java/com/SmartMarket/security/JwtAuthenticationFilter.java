package com.SmartMarket.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.SmartMarket.dto.StoreIdDto;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsername(token);

                // Kullanıcıyı database’den getir
                var userDetails = customUserDetailsService.loadUserByUsername(username);
                int storeId = jwtTokenProvider.getStoreId(token);

             // Ek bilgiler Authentication içine set edilecek
             UsernamePasswordAuthenticationToken authentication =
                 new UsernamePasswordAuthenticationToken(
                     new StoreIdDto(userDetails.getUsername(), storeId),
                     null,
                     userDetails.getAuthorities()
                 );

                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Kullanıcıyı SecurityContext'e kaydet
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Diğer filtrelere geç
        filterChain.doFilter(request, response);
    }
}
