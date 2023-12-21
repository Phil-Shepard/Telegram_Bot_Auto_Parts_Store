package com.urfu.domain.basket;

import com.urfu.domain.message.MessageToUser;
import com.urfu.domain.sparePart.SparePart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Basket {
    public HashMap<String, ArrayList<SparePart>> contentsBasket = new HashMap<>();

    /**
     * Добавляет выбранную запчасть в корзину.
     */
    public MessageToUser addSparePartInBasket(MessageToUser messageToUser, String carName, SparePart sparePart) {
        MessageToUser resultMessageToUser = messageToUser.clone();
        if (Objects.equals(carName, "")){
            resultMessageToUser.setText("Машина не выбрана, необходимо выбрать автомобиль.");
            return resultMessageToUser;
        }
        ArrayList<SparePart> partsList = contentsBasket.getOrDefault(carName, new ArrayList<>());
        partsList.add(sparePart);
        contentsBasket.put(carName, partsList);
        resultMessageToUser.setText("Товар \"" + sparePart.getName() + "\" успешно добавлен в корзину.");
        return resultMessageToUser;
    }

    /**
     * Возвращает содержимое корзины
     */
    public MessageToUser getBasket(MessageToUser messageToUser) {
        MessageToUser resultMessageToUser = messageToUser.clone();
        StringBuilder basketContents = new StringBuilder("Содержимое корзины:\n");

        if(contentsBasket.isEmpty()){
            resultMessageToUser.setText("Корзина пустая.");
            return resultMessageToUser;
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

        resultMessageToUser.setText(basketContents.toString());
        return resultMessageToUser;
    }

    public MessageToUser deleteSpareParts(MessageToUser messageToUser, String input) {
        MessageToUser resultMessageToUser = messageToUser.clone();
        String[] parts = input.split(":");
        if (parts.length != 2) {
            resultMessageToUser.setText("Неверный формат ввода. Используйте /delete Машина: запчасть");
            return resultMessageToUser;
        }

        String carName = parts[0].trim();
        String partName = parts[1].trim();

        if (contentsBasket.containsKey(carName)) {
            ArrayList<SparePart> partsList = contentsBasket.get(carName);

            boolean partRemoved = partsList.removeIf(part -> part.getName().equalsIgnoreCase(partName));

            if (partRemoved) {
                contentsBasket.put(carName, partsList);
                resultMessageToUser.setText("Товар " + carName + ": " + partName + " удален из корзины");
            } else {
                resultMessageToUser.setText("Запчасть " + partName + " не найдена в корзине для машины " + carName);
            }
        } else {
            resultMessageToUser.setText("Машина " + carName + " не найдена в корзине.");
        }

        return resultMessageToUser;
    }


    /**
     * Полностью очищает корзину
     * @return уведомление о том, что корзина очищена.
     */
    public MessageToUser deleteAllPartsFromBasket(MessageToUser messageToUser) {
        MessageToUser resultMessageToUser = messageToUser.clone();
        contentsBasket.clear();
        resultMessageToUser.setText("Корзина очищена");
        return resultMessageToUser;
    }

    /**
     * Создаёт отчёт и вносит его в историю заказов, очищая корзину
     * @return Сообщение об успешном запасе, также возвращает содержимое заказа
     */
    public MessageToUser makeOrder(MessageToUser messageToUser){
        MessageToUser resultMessageToUser = messageToUser.clone();
        if (!contentsBasket.isEmpty()){
            resultMessageToUser.setText("Успешно заказано:" + "\n" + getBasket(messageToUser)
                    .getText().replace("Содержимое корзины:\n", ""));
            return resultMessageToUser;
        }
        resultMessageToUser.setText("Нечего заказывать, корзина пустая");
        return resultMessageToUser;
    }

//    public String getHistory(HistoryServiceImpl historyService){
//        StringBuilder result = new StringBuilder();
//        result.append("История ваших заказов:\n");
//        for (HashMap<String, String> order : historyService.historyList) {
//            for (Map.Entry<String, String> entry : order.entrySet()) {
//                result.append(entry.getKey()).append(":\n");
//                result.append(entry.getValue().replaceAll("(\\d+\\))", "$1"));
//            }
//        }
//        return result.toString();
//    }
}
