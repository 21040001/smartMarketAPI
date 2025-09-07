package com.SmartMarket.telegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.SmartMarket.serviceLayer.StoresServiceInterface;

import java.util.*;

@Component
public class TelegramBot extends TelegramLongPollingBot {

	// Kullanıcı chatId ve login durumunu saklamak için
	private Map<Long, Boolean> loggedInUsers = new HashMap<>();

	@Autowired
	private StoresServiceInterface service;
	
	@Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

	@Override
	public void onUpdateReceived(Update update) {

		if (update.hasCallbackQuery()) {
			handleCallback(update);
		} else if (update.hasMessage() && update.getMessage().hasText()) {
			handleMessage(update);
		}
	}

	// Mesajları işleyen metod
	private void handleMessage(Update update) {
		long chatId = update.getMessage().getChatId();
		String text = update.getMessage().getText();

		if (text.equals("/start")) {
			sendLoginMenu(chatId);

		} else if (text.contains(":")) {
			// username:password formatında giriş kontrol
			String[] parts = text.split(":");
			if (parts.length == 2) {
				String username = parts[0].trim();
				String password = parts[1].trim();

				if (checkLogin(username, password)) {
					loggedInUsers.put(chatId, true);

					// Kullanıcıya giriş başarılı mesajı
					sendMessageTo(chatId, "Kirish muaffaqiyatli! Ona menyuga yo'naltirilyabsiz...");

					try {
						// username aslında store_id ise buradan DB güncelle
						int storeId = Integer.parseInt(username);
						service.updateChatId(storeId, chatId);

						// Menüye yönlendir
						sendMainMenu(chatId);
					} catch (Exception ex) {
						System.out.println("ChatId id'sini yangilashta xato o'rtaga chiqdi: " + ex.getMessage());
						sendMessageTo(chatId, "Sistem xatosi o'rtaga chiqdi.");
					}
				} else {
					sendMessageTo(chatId, "Xatoli kirish! Iltimos, qaytadan sinab ko'ring.");
				}
			} else {
				sendMessageTo(chatId, "Iltimos foydalanuvchi ismingizni va parolingizni to'gri formatta kiriting! username:password shu shaklda kiriting.");
			}

		} else {
			// login olmayan kullanıcı uyarısı
			if (!loggedInUsers.getOrDefault(chatId, false)) {
				sendMessageTo(chatId, "Iltimos avval kirishni amalga oshiring. (/start)");
			} else {
				sendMessageTo(chatId, "Tanlovingiz: " + text);
			}
		}
	}

	// Callback verilerini işleyen metod (Inline Keyboard için)
	private void handleCallback(Update update) {
		long chatId = update.getCallbackQuery().getMessage().getChatId();
		String data = update.getCallbackQuery().getData();

		if (data.equals("LOGIN")) {
			sendMessageTo(chatId, "Iltimos foydalanuvchi ismingiz va parolingizni shu shaklda kiriting: \nusername:password");
		} else if (data.startsWith("MENU_")) {
			sendMessageTo(chatId, "Tanlangan menu: " + data);
		}
	}

	// Login menüsü
	private void sendLoginMenu(long chatId) {
		SendMessage message = new SendMessage();
		message.setChatId(String.valueOf(chatId));
		message.setText("Iltimos kirish qiling:");

		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
		InlineKeyboardButton loginBtn = new InlineKeyboardButton();
		loginBtn.setText("Login");
		loginBtn.setCallbackData("LOGIN");

		markup.setKeyboard(Collections.singletonList(Collections.singletonList(loginBtn)));
		message.setReplyMarkup(markup);

		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	// Ana menü
	private void sendMainMenu(long chatId) {
		SendMessage message = new SendMessage();
		message.setChatId(String.valueOf(chatId));
		message.setText("Ona munyu: Iltimos bir tanlov qiling");

		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

		InlineKeyboardButton btn1 = new InlineKeyboardButton();
		btn1.setText("Seçenek 1");
		btn1.setCallbackData("MENU_1");

		InlineKeyboardButton btn2 = new InlineKeyboardButton();
		btn2.setText("Seçenek 2");
		btn2.setCallbackData("MENU_2");

		markup.setKeyboard(Collections.singletonList(Arrays.asList(btn1, btn2)));
		message.setReplyMarkup(markup);

		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	// Mesaj gönderme metodu
	public void sendMessage(int storeId, String text) {
		SendMessage message = new SendMessage();
		long chatId = service.getChatId(storeId);
		message.setChatId(String.valueOf(chatId));
		message.setText(text);
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	// Mesaj gönderme metodu
	public void sendMessageTo(long chatId, String text) {
		SendMessage message = new SendMessage();
		message.setChatId(String.valueOf(chatId));
		message.setText(text);
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	// Giriş kontrol metodu (örnek)
	private boolean checkLogin(String username, String password) {
		// Buraya kendi kullanıcı doğrulama kodunuzu ekleyin
		return username.equals("123") && password.equals("1234");
	}

}
