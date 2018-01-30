package com.gitlab.graalogosh.cybertask.controllers.telegram;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

/**
 * Created by Anton Mukovozov (graalogosh@gmail.com) on 31.01.2018.
 */
@Component
@Data
public class TelegramBotController extends TelegramLongPollingBot {
    @Value("${telegrambot.token}")
    private String token;

    @Value("${telegrambot.username}")
    private String username;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            System.out.println(message.getText());
        }
    }


}
