package com.artg.coffeetgbot;

import com.artg.coffeetgbot.config.BotConfig;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Log4j2
public class CoffeeBot extends TelegramLongPollingBot {
    final BotConfig botConfig;

    public CoffeeBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getFrom().getUserName();

            switch (messageText) {
                case "/start":
                    startBot(chatId, userName);
                    break;
                default:
                    log.info("Неизвестная команда");
            }
        }
    }

    private void startBot(long chatId, String userName) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText("Hello, " + userName);
        try {
            execute(response);
            log.info("bot started!");
        } catch (TelegramApiException ex) {
            log.error(ex.getMessage());
        }
    }

}
