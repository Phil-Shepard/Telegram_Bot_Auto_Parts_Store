package com.urfu.domain.basket;

import com.urfu.domain.car.Car;
import com.urfu.domain.sparePart.SparePart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {
    public HashMap<String, ArrayList<SparePart>> contentsBasket = new HashMap<>();

    /**
     * Добавляет выбранную запчасть в корзину.
     * @param carName машина, sparePart запчасть
     * @return заполненную корзину
     */


//    /**
//     * Удаляет выбранную запчасть из корзины.
//     * @param basket корзина, из которой будем удалять.
//     * @param sparePart запчасть, которую будем удалять.
//     * @return уведомление о том, что товар удалён из корзины.
//     */
//    public String deletePartFromBasket(BasketServiceImpl basket, String sparePart) {
//        String message = sparePart;
//        String[] listMessage = message.split(" ");
//        if (basket.contentsBasket.containsKey(listMessage[0])) {
//            String value = basket.contentsBasket.get(listMessage[0]);
//            value = value.replace(listMessage[1], ""); // Удаление подстроки
//            value = value.replaceAll("\n{2,}", "\n"); // Удаление лишних пустых строк
//            basket.contentsBasket.put(listMessage[0], value);
//        }
//        return "Товар " + sparePart + " удален из корзины.";
//    }
//
//    /**
//     * После удаления запчасти из корзины этот метод формирует читабельный список в корзине:
//     * правильно номерует оставшиеся в корзине запчасти и удаляет лишние отступы.
//     * @param map корзина, которую метод форматирует.
//     * @param key название машины, запчасть которой до этого была удалена.
//     */
//    public void updateValue(HashMap<String, String> map, String key) {
//        if (map.containsKey(key)) {
//            String value = map.get(key);
//            // Удаляем пустые строки
//            value = value.replaceAll("(?m)^[ \t]*\r?\n", "");
//
//            // Разделяем строки по числам и сортируем по числам
//            String[] lines = value.split("\n");
//            TreeMap<Integer, String> sortedLines = new TreeMap<>();
//            for (String line : lines) {
//                int number = Integer.parseInt(line.split("\\)")[0].replaceAll("[^0-9]", ""));
//                sortedLines.put(number, line);
//            }
//
//            // Формируем новое значение
//            StringBuilder newValue = new StringBuilder();
//            int count = 1;
//            for (String line : sortedLines.values()) {
//                newValue.append(count).append(")").append(line.split("\\)")[1]).append("\n");
//                count++;
//            }
//            map.put(key, newValue.toString().trim());
//        }
//    }
//
//    /**
//     * Полностью очищает корзину
//     * @param basket корзина
//     * @return уведомление о том, что корзина очищена.
//     */
//    public String deleteAllPartsFromBasket(BasketServiceImpl basket) {
//        basket.contentsBasket.clear();
//        return "Корзина очищена";
//    }
//
//    /**
//     * Этот метод нужен для того, чтобы мы могли заказать запчасти для одного автомобиля, затем для второго
//     * и когда вернёмся к первому и начнём снова заказывать наша корзина будет правильно считать последовательность
//     * с нужного нам числа
//     * @param basket корзина, в которой мы храним товары
//     * @param key Машина, к выбору товаров для которой мы вернулись
//     * @return Число, которое показывает, какая по счёту будет следующая запчасть для выбранной машины
//     */
//    public int getSpartCount(BasketServiceImpl basket, String key) {
//        int number = 0;
//        if (basket.contentsBasket.isEmpty() || !basket.contentsBasket.containsKey(key)){
//            return 1;
//        }
//        String basketString = basket.contentsBasket.get(key);
//
//        // Разбиваем строку на подстроки по символу ") "
//        String[] parts = basketString.split("\\) ");
//
//        // Выбираем последнюю подстроку
//        String lastPart = parts[parts.length - 1];
//
//        // Извлекаем цифру из последней подстроки
//        String[] lastPartParts = lastPart.split("\\) ");
//        String lastNumber = lastPartParts[0];
//        String[] words = lastNumber.split("\\D+"); // Разделяем строку по нечисловым символам
//        for (String word : words) {
//            if (!word.isEmpty()) {
//                number = Integer.parseInt(word); // Преобразуем строку в число
//                break;
//            }
//        }
//        return number + 1;
//    }
//
//    /**
//     * Создаёт отчёт и вносит его в историю заказов, очищая корзину
//     * @param basket корзина, товары из которой попадают в отчёт
//     * @param history история заказов, которую мы заполняем
//     * @return Сообщение об успешном запасе, также возвращает содержимое заказа
//     */
//    public String makeOrder(BasketServiceImpl basket, HistoryServiceImpl history){
//        history.historyList.add(basket.contentsBasket);
//        return "Успешно заказано:" + "\n" + getBasket(basket);
//    }
//
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
