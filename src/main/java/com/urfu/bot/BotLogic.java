package com.urfu.bot;

import com.urfu.domain.message.MessageFromUser;
import com.urfu.domain.message.MessageToUser;
import com.urfu.services.CarService;

import static com.urfu.bot.Constants.*;

/**
 * Логика бота
 */
public class BotLogic {
    private final BotMessageCreator botMessageCreator = new BotMessageCreator();
    private final CarService carService = new CarService();
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
            MessageToUser resultMessage;
            switch (messageText) {
                case COMMAND_START -> {
                    resultMessage = botMessageCreator.createMessageStartWorkBot(chatId, messageFromUser.getUserName());
                }
                case COMMAND_EXIT -> {
                    resultMessage = botMessageCreator.createMessageDeleteButtons(chatId);
                }
                case COMMAND_SHOP -> {
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfCars(chatId, carService);
                }
                case "BMW" -> {
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfSpareParts(chatId, carService.getCar("BMW"));
                }
                case "Renault" -> {
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfSpareParts(chatId, carService.getCar("Renault"));
                }
                case "Lada" -> {
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfSpareParts(chatId, carService.getCar("Lada"));
                }
                case COMMAND_HELP -> {
                    resultMessage = botMessageCreator.createMessageAccessButtons(chatId);
                }
                default -> {
                    resultMessage = botMessageCreator.createMessageNotFoundCommand(chatId);
                }
            }

            bot.sendMessage(resultMessage);
        }
    }
}
