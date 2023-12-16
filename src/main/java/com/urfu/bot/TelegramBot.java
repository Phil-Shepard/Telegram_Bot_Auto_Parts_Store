package com.urfu.bot;

import com.urfu.commands.Commands;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Телеграмм бот
 */
public class TelegramBot extends TelegramLongPollingBot implements Bot {

    private final Commands commands = new Commands(this);
    private final BotConfig config = new BotConfig();

    public TelegramBot() throws TelegramApiException {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Это телеграмм бот магазина автозапчастей."));
        listOfCommands.add(new BotCommand("/shop", "Перейти в каталог запчастей"));
        listOfCommands.add(new BotCommand("/add", "Добавить в корзину выбранную запчасть"));
        listOfCommands.add(new BotCommand("/basket", "Вывести содержимое корзины"));
        listOfCommands.add(new BotCommand("/order", "Оформить заказ"));
        listOfCommands.add(new BotCommand("/history", "Вывести историю заказов"));
        listOfCommands.add(new BotCommand("/delete", "Удалить из корзины выбранные комплектующие"));
        listOfCommands.add(new BotCommand("/exit", "Выйти из каталога запчастей"));
        listOfCommands.add(new BotCommand("/help", "Справка"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e){
            throw new TelegramApiException(e.getMessage());
        }
    }

    @Override
    public void sendMessage(MessageToUser messageToUser) {
        try {
            execute(convertMessageToUserToSendMessage(messageToUser));
        } catch (TelegramApiException e) {
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
        MessageFromUser message = new MessageFromUser(
                update.getMessage().getChatId(),
                update.getMessage().getText(),
                update.getMessage().getChat().getFirstName()
        );

        commands.onUpdateReceived(message);
    }

    /**
     * Мапит общий тип сообщения к сообщению ТГ бота
     *
     * @param messageToUser общее сообщение
     * @return Сообщение для ТГ бота
     */
    private SendMessage convertMessageToUserToSendMessage(MessageToUser messageToUser) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(messageToUser.getChatId());

        if (messageToUser.getReplyMarkup()) sendMessage.setReplyMarkup(removeKeyboard());
        if (messageToUser.getText() != null) sendMessage.setText(messageToUser.getText());
        if (messageToUser.getButtonNamesSeparatedBySpaces() != null)
            sendMessage.setReplyMarkup(getKeyBoard(messageToUser.getButtonNamesSeparatedBySpaces()));

        return sendMessage;
    }

    /**
     * Формирует и выводит кнопки
     */
    private ReplyKeyboardMarkup getKeyBoard(String listCarsOrParts) {
        String[] buttons = listCarsOrParts.split(" ");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (String button : buttons) {
            row.add(button);
        }
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    /**
     * Удаляет кнопки
     */
    private ReplyKeyboardRemove removeKeyboard() {
        ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
        keyboardMarkup.setRemoveKeyboard(true);
        return keyboardMarkup;
    }
}
