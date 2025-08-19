package com.SmartMarket.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.security.JwtTokenProvider;
import com.SmartMarket.telegramBot.TelegramBot;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private TelegramBot bot;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private HttpServletRequest request;

	// Header’dan JWT token çek
	private String getToken() {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7); // "Bearer " kısmını at
		}
		return null;
	}

	private int getCurrentStoreId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
		return principal.getStoreId();
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Map<String, String>> handleAccessDeniedException(
	        AccessDeniedException ex,
	        HandlerMethod handlerMethod) {

	    String token = getToken();
	    String username = jwtTokenProvider.getUsername(token);
	    String method = handlerMethod.getMethod().getName();
	    String message = "";

	    switch (method) {
	        case "updateStore":
	            message = "🏬 [" + username + "] foydalanuvchi do‘kon ma’lumotlarini yangilashga urindi, ❌ ammo bu amalni bajarishga ruxsati yo‘q.";
	            break;
	        case "addProduct":
	            message = "➕🛒 [" + username + "] foydalanuvchi yangi mahsulot qo‘shishga urindi, ❌ ammo bu amalni bajarishga ruxsati yo‘q.";
	            break;
	        case "deleteProduct":
	            message = "🗑️ [" + username + "] foydalanuvchi mavjud mahsulotni o‘chirishga urindi, ❌ ammo bu amalni bajarishga ruxsati yo‘q.";
	            break;
	        case "updateProduct":
	            message = "✏️📦 [" + username + "] foydalanuvchi mahsulot ma’lumotlarini yangilashga urindi, ❌ ammo bu amalni bajarishga ruxsati yo‘q.";
	            break;
	        case "updatePassword":
	            message = "🔑 [" + username + "] foydalanuvchi do‘kon parolini yangilashga urindi, ❌ ammo bu amalni bajarishga ruxsati yo‘q.";
	            break;
	        default:
	            message = "⚠️ [" + username + "] foydalanuvchi tomonidan ruxsatsiz amal bajarishga urinish aniqlandi.";
	    }

	    // 🔹 Botga xabar yuborish
	    try {
	        int storeId = getCurrentStoreId();
	        bot.sendMessage(storeId, message);
	    } catch (Exception e) {
	        System.out.println("Bot xabari yuborilmadi : " + e.getMessage());
	    }

	    //  Frontend uchun JSON javob
	    Map<String, String> response = new HashMap<>();
	    response.put("error", "403 - Ruxsat yo‘q");
	    response.put("detail", message);

	    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}

}
