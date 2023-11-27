package com.urfu.bot.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import static com.urfu.bot.telegram.Constants.*;

/**
 * Телеграмм бот
 */
public class TelegramBot extends TelegramLongPollingBot implements Bot {

    private final Logic logic = new Logic(this);
    private final BotConfig config = new BotConfig();

    public TelegramBot() {
        try{
            this.execute(new SetMyCommands(listOfCommands(), new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e){
            System.out.println(e);
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
