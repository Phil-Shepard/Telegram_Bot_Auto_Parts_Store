package telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.bot.Logic;
import telegram.bot.TelegramBot;
import telegram.cars.Car;
import telegram.config.BotConfig;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        BotConfig config = new BotConfig();
        TelegramBotsApi bot = new TelegramBotsApi(DefaultBotSession.class);
        bot.registerBot(new TelegramBot(config));
        Logic logic = new Logic();
        for (Car car:logic.getCars()
             ) {
            System.out.println(car.AvailabilityParts());
        }
    }
}