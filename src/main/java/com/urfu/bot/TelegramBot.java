package com.urfu.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Телеграмм бот
 */
public class TelegramBot extends TelegramLongPollingBot implements Bot {

    private final Logic logic = new Logic(this);
    private final BotConfig config = new BotConfig();

    public TelegramBot() throws TelegramApiException {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Это телеграмм бот магазина автозапчастей."));
        listOfCommands.add(new BotCommand("/shop","Перейти в каталог запчастей"));
        listOfCommands.add(new BotCommand("/add","Добавить в корзину выбранную запчасть"));
        listOfCommands.add(new BotCommand("/basket","Вывести содержимое корзины"));
        listOfCommands.add(new BotCommand("/order","Оформить заказ"));
        listOfCommands.add(new BotCommand("/history","Вывести историю заказов"));
        listOfCommands.add(new BotCommand("/delete","Удалить из корзины выбранные комплектующие"));
        listOfCommands.add(new BotCommand("/exit","Выйти из каталога запчастей"));
        listOfCommands.add(new BotCommand("/help","Справка"));
        try{
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e){
            throw new TelegramApiException("Ошибка работы бота!");
        }
    }
    @Override
    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        }
        catch (TelegramApiException e) {
            System.out.println(e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        logic.onUpdateReceived(update);
    }
}
