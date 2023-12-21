package com.urfu.bot;

import com.urfu.domain.basket.Basket;
import com.urfu.domain.car.Car;
import com.urfu.domain.history.History;
import com.urfu.domain.message.MessageToUser;
import com.urfu.domain.sparePart.SparePart;
import com.urfu.services.CarService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.urfu.bot.Constants.HELP;
import static com.urfu.bot.Constants.TEXT_START;

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

    /**
     * Возвращает сообщение об успешном добавлении запчасти в корзину
     */
    public MessageToUser MessageAddSparePartInBasket(long chatId, String carName, SparePart sparePart, Basket basket) {
        String answer;
        if (Objects.equals(carName, "")){
            answer = "Машина не выбрана, необходимо выбрать автомобиль.";
            return new MessageToUser(chatId, answer);
        }
        basket.addSparePartInBasket(carName, sparePart);
        answer = "Товар \"" + sparePart.getName() + "\" успешно добавлен в корзину.";
        return new MessageToUser(chatId, answer);
    }

    /**
     * Возвращает содержимое корзины в виде сообщения
     */
    public MessageToUser getMessageBasketContents(long chatId, Basket basket) {
        String answer;
        StringBuilder basketContents = new StringBuilder("Содержимое корзины:\n");

        if (basket.getBasket().isEmpty()) {
            answer = "Корзина пустая.";
            return new MessageToUser(chatId, answer);
        }

        Map<String, Map<String, Integer>> partsCount = new HashMap<>();
        for (Map.Entry<String, ArrayList<SparePart>> entry : basket.getBasket().entrySet()) {
            String car = entry.getKey();
            ArrayList<SparePart> spareParts = entry.getValue();
            Map<String, Integer> partsMap = new HashMap<>();

            for (SparePart part : spareParts) {
                String partName = part.getName();
                partsMap.put(partName, partsMap.getOrDefault(partName, 0) + 1);
            }

            partsCount.put(car, partsMap);
        }

        for (Map.Entry<String, Map<String, Integer>> entry : partsCount.entrySet()) {
            String car = entry.getKey();
            Map<String, Integer> partsMap = entry.getValue();

            basketContents.append("Машина: ").append(car).append("\n");
            basketContents.append("Запчасти:\n");

            for (Map.Entry<String, Integer> partEntry : partsMap.entrySet()) {
                String partName = partEntry.getKey();
                int count = partEntry.getValue();
                basketContents.append(partName).append(" - ").append(count).append("\n");
            }

            basketContents.append("\n");
        }

        answer = basketContents.toString();
        return new MessageToUser(chatId, answer);
    }


    /**
     * Удаляет выбранную запчасть из корзины и возвращает сообщение
     * об успешном удалении
     */
    public MessageToUser MessageDeleteSparePartsFromBasket(long chatId, String input, Basket basket) {
        String answer;
        String[] parts = input.split(":");
        if (parts.length != 2) {
            answer = "Неверный формат ввода. Используйте /delete Машина: запчасть";
            return new MessageToUser(chatId, answer);
        }

        String carName = parts[0].trim();
        String partName = parts[1].trim();

        if (basket.getBasket().containsKey(carName)) {
            ArrayList<SparePart> partsList = basket.getBasket().get(carName);

            boolean partRemoved = partsList.removeIf(part -> part.getName().equalsIgnoreCase(partName));

            if (partRemoved) {
                basket.getBasket().put(carName, partsList);
                answer = "Товар " + carName + ": " + partName + " удален из корзины";
            } else {
                answer = "Запчасть " + partName + " не найдена в корзине для машины " + carName;
            }
        } else {
            answer = "Машина " + carName + " не найдена в корзине.";
        }

        return new MessageToUser(chatId, answer);
    }

    /**
     * Полностью очищает корзину
     * @return уведомление о том, что корзина очищена.
     */
    public MessageToUser MessageDeleteAllPartsFromBasket(long chatId, Basket basket) {
        basket.deleteAllPartsFromBasket();
        String answer;
        answer = "Корзина очищена";
        return new MessageToUser(chatId, answer);
    }

    /**
     * Создаёт отчёт и вносит его в историю заказов, очищая корзину
     * @return Сообщение об успешном запасе, также возвращает содержимое заказа
     */
    public MessageToUser makeOrder(long chatId, History history, Basket basket){
        String answer;
        if (!basket.getBasket().isEmpty()){
            answer = "Успешно заказано:" + "\n" + getMessageBasketContents(chatId, basket)
                    .getText().replace("Содержимое корзины:\n", "");
            history.getHistoryList().add(answer
                    .replace("Успешно заказано:", ""));
            return new MessageToUser(chatId, answer);
        }
        answer = "Нечего заказывать, корзина пустая";
        return new MessageToUser(chatId, answer);
    }
}
