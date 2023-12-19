package com.urfu.bot;

import com.urfu.domain.message.MessageFromUser;
import com.urfu.domain.message.MessageToUser;
import com.urfu.services.CarService;
import com.urfu.services.SparePartService;

import static com.urfu.bot.Constants.*;

/**
 * Логика бота
 */
public class BotLogic {
    private final BotCommands botCommands = new BotCommands();
    private final CarService carService = new CarService();
    private final SparePartService sparePartService = new SparePartService();
    private final Bot bot;

    public BotLogic(Bot bot) {
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
            MessageToUser resultMessage;
            switch (messageText) {
                case COMMAND_START -> {
                    resultMessage = botCommands.startCommandReceived(message, messageFromUser.getUserName());
                }
                case COMMAND_EXIT -> {
                    resultMessage = botCommands.deleteButtons(message);
                }
                case COMMAND_SHOP -> {
                    resultMessage = botCommands.setNamesButtonsAndSetTextNamesOfShop(message, carService);
                }
                case "BMW" -> {
                    resultMessage = botCommands.setNamesButtonsAndSetTextNamesOfCars(message, carService.getCar("BMW"), sparePartService);
                }
                case "Renault" -> {
                    resultMessage = botCommands.setNamesButtonsAndSetTextNamesOfCars(message, carService.getCar("Renault"), sparePartService);
                }
                case "Lada" -> {
                    resultMessage = botCommands.setNamesButtonsAndSetTextNamesOfCars(message, carService.getCar("Lada"), sparePartService);
                }
                case COMMAND_HELP -> {
                    resultMessage = botCommands.setTextHelp(message);
                }
                default -> {
                    resultMessage = botCommands.setTextCommandNotFound(message);
                }
            }

            bot.sendMessage(resultMessage);
        }
    }
}
