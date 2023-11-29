package com.urfu.bot.telegram;

import com.urfu.bot.commands.Commands;
import com.urfu.bot.services.basket.BasketServiceImpl;
import com.urfu.bot.services.car.CarServiceImpl;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import static com.urfu.bot.telegram.Constants.*;

/**
 * Описывает логику бота.
 */
public class Logic {
    private final Commands commands = new Commands();
    private final Bot bot;
    private BasketServiceImpl basket = new BasketServiceImpl();

    private int countParts = 1;

    private String nameCar = "";

    public Logic(Bot bot){
        this.bot = bot;
    }

    /**
     *  Точка входа, куда будут поступать сообщения от пользователей. Отсюда будет идти вся новая логика.
     * @param update
     */
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            CarServiceImpl carService = new CarServiceImpl();
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            switch (messageText){
//                case command_delete_wheels:
//                    message.setText(commands.deletePartFromBasket(basket, "колёса"));
//                    bot.sendMessage(message);
//                    break;
                case command_wheels:
                    message.setText(commands.addSparePartInBasket(basket, nameCar,"колёса", countParts));
                    countParts++;
                    bot.sendMessage(message);
                    break;
                case command_wipers:
                    message.setText(commands.addSparePartInBasket(basket, nameCar,"дворники", countParts));
                    countParts++;
                    bot.sendMessage(message);
                    break;
                case command_headlights:
                    message.setText(commands.addSparePartInBasket(basket, nameCar,"фары", countParts));
                    countParts++;
                    bot.sendMessage(message);
                    break;
                case command_basket:
                    message.setText(commands.getBasket(basket));
                    bot.sendMessage(message);
                    break;
                case command_start:
                    commands.startCommandReceived(message, update.getMessage().getChat().getFirstName());
                    bot.sendMessage(message);
                    break;
                case command_exit:
                    message.setReplyMarkup(commands.removeKeyboard());
                    message.setText("Вы закрыли каталог товаров");
                    bot.sendMessage(message);
                    break;
                case command_shop:
                    commands.GetShop(message, carService);
                    bot.sendMessage(message);
                    break;
                case "BMW":
                    nameCar = "BMW";
                    commands.takeCarParts(message, carService.getCar("BMW"));
                    bot.sendMessage(message);
                    break;
                case "Renault":
                    nameCar = "Renault";
                    commands.takeCarParts(message, carService.getCar("Renault"));
                    bot.sendMessage(message);
                    break;
                case "Lada":
                    nameCar = "Lada";
                    commands.takeCarParts(message, carService.getCar("Lada"));
                    bot.sendMessage(message);
                    break;
                case command_help:
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
