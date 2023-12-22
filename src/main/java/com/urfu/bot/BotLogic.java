package com.urfu.bot;

import com.urfu.domain.message.MessageFromUser;
import com.urfu.domain.message.MessageToUser;
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
    private final SpareService spareService = new SpareService();
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
                    resultMessage = botMessageCreator.createMessageStartWorkBot(
                            chatId,
                            messageFromUser.getUserName()
                    );
                }

                case COMMAND_EXIT -> {
                    resultMessage = botMessageCreator.createMessageDeleteButtons(chatId);
                }

                case COMMAND_SHOP -> {
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfCars(
                            chatId,
                            carService
                    );
                }

                case "BMW" -> {
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfSpareParts(
                            chatId,
                            carService.getCar("BMW"));
                }

                case "Renault" -> {
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfSpareParts(
                            chatId,
                            carService.getCar("Renault")
                    );
                }

                case "Lada" -> {
                    resultMessage = botMessageCreator.createMessageNamesButtonsAndTextNamesOfSpareParts(
                            chatId,
                            carService.getCar("Lada")
                    );
                }

                case COMMAND_HELP -> {
                    resultMessage = botMessageCreator.createMessageAccessButtons(chatId);
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
                    if (messageText.startsWith(COMMAND_COUNT)) {
                        String[] parts = messageText.split("/");
                        if (parts.length != 4) {
                            resultMessage = botMessageCreator.createMessageNotFoundCommand(chatId);
                            break;
                        }
                        LocalDate startDate;
                        LocalDate endDate;
                        try {
                            startDate = LocalDate.parse(parts[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            endDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
                    } else {
                        resultMessage = botMessageCreator.createMessageNotFoundCommand(chatId);
                    }
                }
            }

            bot.sendMessage(resultMessage);
        }
    }
}
