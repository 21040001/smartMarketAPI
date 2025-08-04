package com.SmartMarket.config;

import com.SmartMarket.security.CustomUserDetailsService;
import com.SmartMarket.security.JwtAuthenticationFilter;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*@Configuration Annatotion bu sinif ichida proyektning boshlang'ich tuzatmalarii 
 * qilinganinini bildiradi yani ishga tushurganimizda ilk ilk ishga tushuriladigan sinif
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	// Foydalanuvchi ma'lumotlarini yuklovchi maxsus servis
	private final CustomUserDetailsService userDetailsService;

	// JWT token filtri
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	// Konstruktor orqali dependency injection
	public SecurityConfig(CustomUserDetailsService userDetailsService,
			JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	// Parolni shifrlash uchun bean
	// BCrypt algoritmi bilan
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}

	// Autentifikatsiya menedjeri
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// Autentifikatsiya provayderi
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService); // Foydalanuvchi servisi
		authProvider.setPasswordEncoder(passwordEncoder()); // Parol shifrlash
		return authProvider;
	}

	// Asosiy xavfsizlik zanjiri
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// CSRF himoyasini o'chirish (REST API uchun kerak emas)
				.csrf(AbstractHttpConfigurer::disable)

				// Sessiyasiz ishlash rejimi
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Autentifikatsiya xatolari uchun ishlovchi
				.exceptionHandling(
						exception -> exception.authenticationEntryPoint((request, response, authException) -> {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), "Kirish mumkin emas");
						}))

				// So'rovlarga ruxsat berish
				.authorizeHttpRequests(auth -> auth
						// Quyidagi endpointlar ruxsatsiz ishlatilishi mumkin
						.requestMatchers("/api/auth/login", // Kirish endpointi
								"/api-docs/**", // Swagger dokumentatsiyasi
								"/swagger-ui/**", // Swagger UI
								"/swagger-ui.html" // Swagger bosh sahifasi
						).permitAll()

						// Qolgan barcha so'rovlar autentifikatsiya talab qiladi
						.anyRequest().authenticated())

				// Autentifikatsiya provayderini qo'shish
				.authenticationProvider(authenticationProvider())

				// JWT filtrini qo'shish
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// MApper otomatik obyekt mappinglari uchun ishlatiladi
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}