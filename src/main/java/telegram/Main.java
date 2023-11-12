package telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.bot.TelegramBot;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi bot = new TelegramBotsApi(DefaultBotSession.class);
        bot.registerBot(new TelegramBot());
    }
}