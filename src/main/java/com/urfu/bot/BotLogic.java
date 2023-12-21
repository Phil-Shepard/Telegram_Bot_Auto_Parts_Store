package com.urfu.bot;

import com.urfu.domain.basket.Basket;
import com.urfu.domain.car.Car;
import com.urfu.domain.message.MessageFromUser;
import com.urfu.domain.message.MessageToUser;
import com.urfu.domain.sparePart.SparePart;
import com.urfu.services.CarService;
import com.urfu.services.HistoryService;
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
    private String nameCar = "";
    private final HistoryService history = new HistoryService();

    public BotLogic(Bot bot) {
        this.bot = bot;
    }

    /**
     * Точка входа, куда будут поступать сообщения от пользователей. Отсюда будет идти вся новая логика.
     */
    public void onUpdateReceived(MessageFromUser messageFromUser, Basket basket) {
        if (messageFromUser.getMessage() != null && !messageFromUser.getMessage().isEmpty()) {
            String messageText = messageFromUser.getMessage();
            long chatId = messageFromUser.getChatId();
            MessageToUser message = new MessageToUser();
            message.setChatId(chatId);
            MessageToUser resultMessage = null;
            if (messageText.startsWith("/delete ")) {
                String argument = messageText.substring("/delete ".length()).trim();
                if (!argument.isEmpty()) {
                    resultMessage = basket.deleteSpareParts(message, argument);
                }
            }
            switch (messageText) {
                case COMMAND_START -> {
                    nameCar = "";
                    resultMessage = botCommands.startCommandReceived(message, messageFromUser.getUserName());
                }
                case COMMAND_EXIT -> {
                    nameCar = "";
                    resultMessage = botCommands.deleteButtons(message);
                }
                case COMMAND_SHOP -> {
                    nameCar = "";
                    resultMessage = botCommands.setNamesButtonsAndSetTextNamesOfShop(message, carService);
                }
                case COMMAND_BASKET -> {
                    resultMessage = basket.getBasket(message);
                }
                case COMMAND_DELETE -> {
                    resultMessage = basket.deleteAllPartsFromBasket(message);
                }
                case COMMAND_ORDER -> {
                    resultMessage = basket.makeOrder(message,history);
                    basket.deleteAllPartsFromBasket(message);
                }
                case COMMAND_HISTORY -> {
                    resultMessage = basket.getHistory(message, history);
                }
                case "BMW" -> {
                    nameCar = "BMW";
                    resultMessage = botCommands.setNamesButtonsAndSetTextNamesOfCars(message, carService.getCar("BMW"), sparePartService);
                }
                case "Renault" -> {
                    nameCar = "Renault";
                    resultMessage = botCommands.setNamesButtonsAndSetTextNamesOfCars(message, carService.getCar("Renault"), sparePartService);
                }
                case "Lada" -> {
                    nameCar = "Lada";
                    resultMessage = botCommands.setNamesButtonsAndSetTextNamesOfCars(message, carService.getCar("Lada"), sparePartService);
                }
                case COMMAND_HELP -> {
                    resultMessage = botCommands.setTextHelp(message);
                }
                case COMMAND_WHEELS -> {
                    resultMessage = basket.addSparePartInBasket(message, nameCar, new SparePart("колёса"));
                }
                case COMMAND_WHIPERS -> {
                    resultMessage = basket.addSparePartInBasket(message, nameCar, new SparePart("дворники"));
                }
                case COMMAND_HEADLIGHTS -> {
                    resultMessage = basket.addSparePartInBasket(message, nameCar, new SparePart("фары"));
                }
                default -> {
                    if (!messageText.startsWith("/delete "))
                        resultMessage = botCommands.setTextCommandNotFound(message);
                }
            }
            bot.sendMessage(resultMessage);
        }
    }
}
