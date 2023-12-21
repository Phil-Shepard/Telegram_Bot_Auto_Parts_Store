package com.urfu.domain.basket;

import com.urfu.domain.message.MessageToUser;
import com.urfu.domain.sparePart.SparePart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Класс корзины, в которой у нас хранятся названия машин и выбранные
 * к ним запчасти в виде словаря.
 * Также в классе находятся все методы взаимодействия с корзиной.
 */
public class Basket {
    public HashMap<String, ArrayList<SparePart>> contentsBasket = new HashMap<>();

    /**
     * Добавляет выбранную запчасть в корзину и возвращает сообщение
     * об успешном добавлении
     */
    public MessageToUser addSparePartInBasket(long chatId, String carName, SparePart sparePart) {
        String answer;
        if (Objects.equals(carName, "")){
            answer = "Машина не выбрана, необходимо выбрать автомобиль.";
            return new MessageToUser(chatId, answer);
        }
        ArrayList<SparePart> partsList = contentsBasket.getOrDefault(carName, new ArrayList<>());
        partsList.add(sparePart);
        contentsBasket.put(carName, partsList);
        answer = "Товар \"" + sparePart.getName() + "\" успешно добавлен в корзину.";
        return new MessageToUser(chatId, answer);
    }

    /**
     * Возвращает содержимое корзины в виде сообщения
     */
    public MessageToUser getBasket(long chatId) {
        String answer;
        StringBuilder basketContents = new StringBuilder("Содержимое корзины:\n");

        if(contentsBasket.isEmpty()){
            answer = "Корзина пустая.";
            return new MessageToUser(chatId, answer);
        }

        Map<String, Map<String, Integer>> partsCount = new HashMap<>();
        for (Map.Entry<String, ArrayList<SparePart>> entry : contentsBasket.entrySet()) {
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
    public MessageToUser deleteSparePartsFromBasket(long chatId, String input) {
        String answer;
        String[] parts = input.split(":");
        if (parts.length != 2) {
            answer = "Неверный формат ввода. Используйте /delete Машина: запчасть";
            return new MessageToUser(chatId, answer);
        }

        String carName = parts[0].trim();
        String partName = parts[1].trim();

        if (contentsBasket.containsKey(carName)) {
            ArrayList<SparePart> partsList = contentsBasket.get(carName);

            boolean partRemoved = partsList.removeIf(part -> part.getName().equalsIgnoreCase(partName));

            if (partRemoved) {
                contentsBasket.put(carName, partsList);
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
    public MessageToUser deleteAllPartsFromBasket(long chatId) {
        String answer;
        contentsBasket.clear();
        answer = "Корзина очищена";
        return new MessageToUser(chatId, answer);
    }
}
