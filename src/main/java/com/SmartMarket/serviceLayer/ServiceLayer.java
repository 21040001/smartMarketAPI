package com.SmartMarket.serviceLayer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.dto.AuthRequest;
import com.SmartMarket.dto.AuthResponse;
import com.SmartMarket.security.CustomUserDetailsService;
import com.SmartMarket.security.JwtTokenProvider;

@Service
public class ServiceLayer implements ServiceLayerInterface {
 
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    
    public ServiceLayer(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                        CustomUserDetailsService userDetailsService) {
        
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;    }

    
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
