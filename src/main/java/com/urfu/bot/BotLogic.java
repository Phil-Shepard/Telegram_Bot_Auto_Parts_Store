package com.urfu.bot;

import com.urfu.domain.basket.Basket;
import com.urfu.domain.history.History;
import com.urfu.domain.message.MessageFromUser;
import com.urfu.domain.message.MessageToUser;
import com.urfu.domain.sparePart.SparePart;
import com.urfu.services.CarService;
import com.urfu.services.SpareService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.urfu.bot.Constants.*;

/**
 * Логика бота
 */
public class BotLogic {
    private final BotMessageCreator botMessageCreator = new BotMessageCreator();
    private final CarService carService = new CarService();
    private final Bot bot;
    private String nameCar = "";
    private final SpareService spareService = new SpareService();

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
                case (COMMAND_COUNT + COMMAND_ANY) -> {
                    String[] parts = messageText.split("/");
                    if (parts.length != 3) {
                        resultMessage = botMessageCreator.createMessageNotFoundCommand(chatId);
                        break;
                    }

                    LocalDate startDate;
                    try {
                        startDate = LocalDate.parse(parts[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    } catch (DateTimeParseException ex) {
                        resultMessage = botMessageCreator.createMessageWithCustomException(
                                chatId,
                                "Вы ввели некорректную дату"
                        );
                        break;
                    }
                    resultMessage = botMessageCreator
                            .createMessageCountProduct(chatId, parts[1], startDate, spareService);
                }

                default -> {
                    if (!messageText.startsWith("/delete ") && !messageText.startsWith(COMMAND_COUNT)){
                        resultMessage = botMessageCreator.createMessageNotFoundCommand(chatId);
                        break;
                    }
                    if (messageText.startsWith(COMMAND_COUNT)) {
                        String[] parts = messageText.split("/");
                        if (parts.length != 5 && parts.length != 4) {
                            resultMessage = botMessageCreator.createMessageNotFoundCommand(chatId);
                            break;
                        }
                        LocalDate startDate;
                        LocalDate endDate;
                        if (parts.length == 5) {
                        try {
                            startDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            endDate = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        } catch (DateTimeParseException ex) {
                            resultMessage = botMessageCreator.createMessageWithCustomException(
                                    chatId,
                                    "Вы ввели некорректную дату"
                            );
                            break;
                        }
                        resultMessage = botMessageCreator.createMessageCountProduct(
                                chatId,
                                parts[1],
                                startDate,
                                endDate, spareService
                        );
                        } else if (parts.length == 4) {
                            try {
                                startDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            } catch (DateTimeParseException ex) {
                                resultMessage = botMessageCreator.createMessageWithCustomException(
                                        chatId,
                                        "Вы ввели некорректную дату"
                                );
                                break;
                            }
                            resultMessage = botMessageCreator
                                    .createMessageCountProduct(chatId, parts[1], startDate, spareService);
                        }
                    } else {
                        resultMessage = botMessageCreator.createMessageNotFoundCommand(chatId);
                    }
                }
            }
            bot.sendMessage(resultMessage);
        }
    }
}
