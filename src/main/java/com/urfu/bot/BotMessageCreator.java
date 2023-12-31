package com.urfu.bot;

import com.urfu.domain.car.Car;
import com.urfu.domain.message.MessageToUser;
import com.urfu.services.CarService;

import static com.urfu.bot.Constants.*;

/**
 * Класс, содержащий методы для создания сообщений от бота для пользователя.
 */
public class BotMessageCreator {

    /**
     * Создается сообщение для пользователя с ответом и кнопками в виде наименования машин,
     * на которые имеются комплектующие.
     */
    public MessageToUser createMessageNamesButtonsAndTextNamesOfCars(long chatId, CarService carService) {
        String answer = "В наличии комплектующие для автомобилей:\n"
                + carService.getNamesCars(", ");
        String buttonNamesSeparatedBySpaces = carService.getNamesCars(" ");
        return new MessageToUser(chatId, answer, false, buttonNamesSeparatedBySpaces);
    }

    /**
     * Создается сообщение для пользователя с ответом и кнопками в виде автозапчастей на выбранный автомобиль.
     */
    public MessageToUser createMessageNamesButtonsAndTextNamesOfSpareParts(long chatId, Car car) {
        String answer = car.getAvailabilityParts(", ");
        String buttonNamesSeparatedBySpaces = car.getAvailabilityParts(" ");
        return new MessageToUser(chatId, answer, false, buttonNamesSeparatedBySpaces);
    }

    /**
     * Создается сообщение для пользователя с текстом приветствия и списком возможных команд бота.
     */
    public MessageToUser createMessageStartWorkBot(long chatId, String name) {
        String answer = "Привет, " + name + ", это телеграм бот магазина автозапчастей.\n" +
                TEXT_START;
        return new MessageToUser(chatId, answer, true, null);
    }

    /**
     * Создается сообщение для пользователя с текстом о доступных командах бота.
     */
    public MessageToUser createMessageAccessButtons(long chatId) {
        return new MessageToUser(chatId, HELP, false, null);
    }

    /**
     * Создается сообщение для пользователя с текстом о закрытии каталога товаров и параметром, указывающим на удаление кнопок
     */
    public MessageToUser createMessageDeleteButtons(long chatId) {
        String answer = "Вы закрыли каталог товаров";
        Boolean removeMarkup = true;
        return new MessageToUser(chatId, answer, removeMarkup, null);
    }

    /**
     * Создается сообщение для пользователя с текстом о недоступной команде.
     */
    public MessageToUser createMessageNotFoundCommand(long chatId) {
        String answer = "Команда не найдена";
        return new MessageToUser(chatId, answer, false, null);
    }
}
