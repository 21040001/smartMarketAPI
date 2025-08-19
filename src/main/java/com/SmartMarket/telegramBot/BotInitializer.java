package com.SmartMarket.telegramBot;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BotInitializer implements CommandLineRunner {

    private final TelegramBot bot;

    public BotInitializer(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void run(String... args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(bot);
            System.out.println("Bot kaydedildi ve çalışıyor...");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
