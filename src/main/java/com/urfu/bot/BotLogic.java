package com.urfu.bot;

import com.urfu.domain.basket.Basket;
import com.urfu.domain.history.History;
import com.urfu.domain.message.MessageFromUser;
import com.urfu.domain.message.MessageToUser;
import com.urfu.domain.sparePart.SparePart;
import com.urfu.services.CarService;

import static com.urfu.bot.Constants.*;

/**
 * Логика бота
 */
public class BotLogic {
    private final BotMessageCreator botMessageCreator = new BotMessageCreator();
    private final CarService carService = new CarService();
    private final Bot bot;
    private String nameCar = "";

    public BotLogic(Bot bot) {
        this.bot = bot;
    }

    /**
     * Точка входа, куда будут поступать сообщения от пользователей. Отсюда будет идти вся новая логика.
     */
    public void onUpdateReceived(MessageFromUser messageFromUser, Basket basket, History history) {
        if (messageFromUser.getMessage() != null && !messageFromUser.getMessage().isEmpty()) {
            String messageText = messageFromUser.getMessage();
            long chatId = messageFromUser.getChatId();
            MessageToUser resultMessage = null;
            if (messageText.startsWith("/delete ")) {
                String argument = messageText.substring("/delete ".length()).trim();
                if (!argument.isEmpty()) {
                    resultMessage = basket.deleteSpareParts(chatId, argument);
                }
            }
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
                case COMMAND_BASKET -> {
                    resultMessage = basket.getBasket(chatId);
                }
                case COMMAND_DELETE -> {
                    resultMessage = basket.deleteAllPartsFromBasket(chatId);
                }
                case COMMAND_ORDER -> {
                    resultMessage = basket.makeOrder(chatId,history);
                    basket.deleteAllPartsFromBasket(chatId);
                }
                case COMMAND_HISTORY -> {
                    resultMessage = basket.getHistory(chatId, history);
                }
                case "BMW" -> {
                    nameCar = "BMW";
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfSpareParts(chatId, carService.getCar("BMW"));
                }
                case "Renault" -> {
                    nameCar = "Renault";
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfSpareParts(chatId, carService.getCar("Renault"));
                }
                case "Lada" -> {
                    nameCar = "Lada";
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfSpareParts(chatId, carService.getCar("Lada"));
                }
                case COMMAND_HELP -> {
                    resultMessage = botMessageCreator.createMessageAccessButtons(chatId);
                }
                case COMMAND_WHEELS -> {
                    resultMessage = basket.addSparePartInBasket(chatId, nameCar, new SparePart("колёса"));
                }
                case COMMAND_WHIPERS -> {
                    resultMessage = basket.addSparePartInBasket(chatId, nameCar, new SparePart("дворники"));
                }
                case COMMAND_HEADLIGHTS -> {
                    resultMessage = basket.addSparePartInBasket(chatId, nameCar, new SparePart("фары"));
                }
                default -> {
                    if (!messageText.startsWith("/delete "))
                        resultMessage = botMessageCreator.createMessageNotFoundCommand(chatId);
                }
            }
            bot.sendMessage(resultMessage);
        }
    }
}
