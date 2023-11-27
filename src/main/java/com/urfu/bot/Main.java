package com.urfu.bot;

import com.urfu.bot.telegram.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi bot = new TelegramBotsApi(DefaultBotSession.class);
        bot.registerBot(new TelegramBot());
    }
}