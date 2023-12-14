package com.urfu.bot.telegram;

import com.urfu.bot.commands.Commands;
import com.urfu.bot.services.car.CarService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import static com.urfu.bot.telegram.Constants.*;

/**
 * Описывает логику бота.
 */
public class Logic {
    private final Commands commands = new Commands();
    private CarService carService = new CarService();
    private final Bot bot;

    public Logic(Bot bot){
        this.bot = bot;
    }

    /**
     *  Точка входа, куда будут поступать сообщения от пользователей. Отсюда будет идти вся новая логика.
     * @param update
     */
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            switch (messageText){
                case COMMAND_START:
                    commands.startCommandReceived(message, update.getMessage().getChat().getFirstName());
                    bot.sendMessage(message);
                    break;
                case COMMAND_EXIT:
                    message.setReplyMarkup(commands.removeKeyboard());
                    message.setText("Вы закрыли каталог товаров");
                    bot.sendMessage(message);
                    break;
                case COMMAND_SHOP:
                    commands.getShop(message, carService);
                    bot.sendMessage(message);
                    break;
                case "BMW":
                    commands.takeCarParts(message, carService.getCar("BMW"));
                    bot.sendMessage(message);
                    break;
                case "Renault":
                    commands.takeCarParts(message, carService.getCar("Renault"));
                    bot.sendMessage(message);
                    break;
                case "Lada":
                    commands.takeCarParts(message, carService.getCar("Lada"));
                    bot.sendMessage(message);
                    break;
                case COMMAND_HELP:
                    message.setText(HELP);
                    bot.sendMessage(message);
                     break;
                default:
                    message.setText("Команда не найдена");
                    bot.sendMessage(message);
            }
        }
    }
}
