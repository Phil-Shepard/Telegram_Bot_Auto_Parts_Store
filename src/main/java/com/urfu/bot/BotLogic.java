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
    private final Basket basket = new Basket();
    private final History history = new History();


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
            MessageToUser resultMessage = null;
            if (messageText.startsWith("/delete ")) {
                String argument = messageText.substring("/delete ".length()).trim();
                if (!argument.isEmpty()) {
                    resultMessage = botMessageCreator.MessageDeleteSparePartsFromBasket(chatId, argument, basket);
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
                    resultMessage = botMessageCreator.getMessageBasketContents(chatId, basket);
                }
                case COMMAND_DELETE -> {
                    resultMessage = botMessageCreator.MessageDeleteAllPartsFromBasket(chatId, basket);
                }
                case COMMAND_ORDER -> {
                    resultMessage = botMessageCreator.makeOrder(chatId,history,basket);
                    basket.deleteAllPartsFromBasket();
                }
                case COMMAND_HISTORY -> {
                    resultMessage = history.getHistory(chatId);
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
                    resultMessage = botMessageCreator.MessageAddSparePartInBasket(chatId, nameCar, new SparePart("колёса"), basket);
                }
                case COMMAND_WHIPERS -> {
                    resultMessage = botMessageCreator.MessageAddSparePartInBasket(chatId, nameCar, new SparePart("дворники"), basket);
                }
                case COMMAND_HEADLIGHTS -> {
                    resultMessage = botMessageCreator.MessageAddSparePartInBasket(chatId, nameCar, new SparePart("фары"), basket);
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
