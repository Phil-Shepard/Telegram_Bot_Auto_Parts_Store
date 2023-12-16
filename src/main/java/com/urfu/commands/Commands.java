package com.urfu.commands;

import com.urfu.bot.Bot;
import com.urfu.bot.MessageFromUser;
import com.urfu.bot.MessageToUser;
import com.urfu.logic.Logic;
import com.urfu.services.CarService;

import static com.urfu.bot.Constants.*;

/**
 * Описывает команды
 */
public class Commands {
    private final Logic logic = new Logic();
    private final CarService carService = new CarService();
    private final Bot bot;

    public Commands(Bot bot) {
        this.bot = bot;
    }

    /**
     * Точка входа, куда будут поступать сообщения от пользователей. Отсюда будет идти вся новая логика.
     */
    public void onUpdateReceived(MessageFromUser messageFromUser) {
        if (messageFromUser.getMessage() != null && !messageFromUser.getMessage().isEmpty()) {
            String messageText = messageFromUser.getMessage();
            long chatId = messageFromUser.getChatId();
            MessageToUser message = new MessageToUser();
            message.setChatId(chatId);

            switch (messageText) {
                case COMMAND_START -> {
                    logic.startCommandReceived(message, messageFromUser.getUserName());
                }
                case COMMAND_EXIT -> {
                    message.setRemoveKeyboard(true);
                    message.setText("Вы закрыли каталог товаров");
                }
                case COMMAND_SHOP -> {
                    logic.setNamesButtonsAndSetTextNamesOfShop(message, carService);
                }
                case "BMW" -> {
                    logic.setNamesButtonsAndSetTextNamesOfCars(message, carService.getCar("BMW"));
                }
                case "Renault" -> {
                    logic.setNamesButtonsAndSetTextNamesOfCars(message, carService.getCar("Renault"));
                }
                case "Lada" -> {
                    logic.setNamesButtonsAndSetTextNamesOfCars(message, carService.getCar("Lada"));
                }
                case COMMAND_HELP -> {
                    message.setText(HELP);
                }
                default -> {
                    message.setText("Команда не найдена");
                }
            }

            bot.sendMessage(message);
        }
    }
}
