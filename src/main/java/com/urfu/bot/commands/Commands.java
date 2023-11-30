package com.urfu.bot.commands;

import com.urfu.bot.services.basket.BasketServiceImpl;
import com.urfu.bot.services.car.CarServiceImpl;
import com.urfu.bot.storage.Storage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import com.urfu.bot.domain.car.Car;

import java.util.*;

/**
 * Класс, содержащий все используемые методы(команды)
 */
public class Commands {
    /**
     * Выводит сообщением наименования машин, на которые имеются комплектующие,
     * а также выводит соответствующие кнопки с наименованием машиин.
     *
     * @param message
     * @param carService
     */
    public void GetShop(SendMessage message, CarServiceImpl carService) {
        String answer = "В наличии комплектующиие для автомобилей: \n" + carService.GetNamesCars();
        message.setReplyMarkup(getKeyBoard(getNameCars()));
        message.setText(answer);
    }

    /**
     * Выводит сообщением и в ввиде кнопок наличие запчастей на выбранный зараее автомобиль.
     *
     * @param message
     * @param car
     */
    public void takeCarParts(SendMessage message, Car car) {
        String answer = car.getAvailabilityParts();
        message.setReplyMarkup(getKeyBoard(getParts(car)));
        message.setText(answer);
    }

    /**
     * Выводит приветствие и список возможных команд.
     *
     * @param message
     * @param name
     */
    public void startCommandReceived(SendMessage message, String name) {
        String answer = "Привет, " + name + ", Это телеграмм бот магазина  автозапчастей." +
                " Доступны следующие команды:\n" +
                "/shop – Перейти в каталог запчастей.\n" +
                "/add - добавить в корзину выбранную запчасть.\n" +
                "/basket - вывести содержимое корзины.\n" +
                "/order - оформить заказ\n" +
                "/history - вывести историю заказов.\n" +
                "/delete - удалить из корзины выбранные комплектующие.\n" +
                "/exit - выйти из каталога запчастей.\n" +
                "/help - Справка.\n";
        message.setReplyMarkup(removeKeyboard());
        message.setText(answer);
    }

    /**
     * Удаляет кнопки.
     *
     * @return
     */
    public ReplyKeyboardRemove removeKeyboard() {
        ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
        keyboardMarkup.setRemoveKeyboard(true);
        return keyboardMarkup;
    }

    /**
     * Формирует и выводит кнопки.
     *
     * @param listCarsOrParts
     * @return
     */
    public ReplyKeyboardMarkup getKeyBoard(String listCarsOrParts) {
        String[] buttons = listCarsOrParts.split(" ");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (String button : buttons
        ) {
            row.add(button);
        }
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    /**
     * Возвращает все названия машин, на которые есть запчасти.
     *
     * @return
     */
    public String getNameCars() {
        Storage storage = new Storage();
        List<Car> listCars = storage.getStorage();
        String nameCars = "";
        for (Car car : listCars
        ) {
            nameCars += car.getName() + " ";
        }
        return nameCars;
    }

    /**
     * Возвращает список имеющихся запчастей на конкретную машину в виде строки.
     *
     * @param car
     * @return
     */
    public String getParts(Car car) {
        String parts = car.getAvailabilityParts();
        return parts.replace(",", "");
    }

    /**
     * Добавляет выбранную запчасть в корзину.
     *
     * @param basket корзина для добавления, sparePart запчасть, count номер запчасти в корзине
     * @return заполненную корзину
     */
    public String addSparePartInBasket(BasketServiceImpl basket, String carName, String sparePart, int sparePartCount) {
        basket.contentsBasket.put(carName, basket.contentsBasket
                .getOrDefault(carName, "") + sparePartCount + ")" + "\"" + sparePart + "\"" + "\n");
        return "Товар " + sparePart + " успешно добавлен в корзину.";
    }

    /**
     * Возвращает содержимое корзины
     * @param basket корзина
     * @return содержимое корзины
     */
    public String getBasket(BasketServiceImpl basket) {
        String answer = "";
        if (basket.contentsBasket.isEmpty())
            return "Ваша корзина пуста, добавьте товар в корзину.";
        for (Map.Entry<String, String> entry : basket.contentsBasket.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            answer += key + ":" + "\n" + value + "\n";
        }
        return answer;
    }

    /**
     * Удаляет выбранную запчасть из корзины.
     * @param basket корзина, из которой будем удалять.
     * @param sparePart запчасть, которую будем удалять.
     * @return уведомление о том, что товар удалён из корзины.
     */
    public String deletePartFromBasket(BasketServiceImpl basket, String sparePart) {
        String message = sparePart;
        String[] listMessage = message.split(" ");
        if (basket.contentsBasket.containsKey(listMessage[0])) {
            String value = basket.contentsBasket.get(listMessage[0]);
            value = value.replace(listMessage[1], ""); // Удаление подстроки
            value = value.replaceAll("\n{2,}", "\n"); // Удаление лишних пустых строк
            basket.contentsBasket.put(listMessage[0], value);
        }
        return "Товар " + sparePart + " удален из корзины.";
    }

    /**
     * После удаления запчасти из корзины этот метод формирует читабельный список в корзине:
     * правильно номерует оставшиеся в корзине запчасти и удаляет лишние отступы.
     * @param map корзина, которую метод форматирует.
     * @param key название машины, запчасть которой до этого была удалена.
     */
    public void updateValue(HashMap<String, String> map, String key) {
        if (map.containsKey(key)) {
            String value = map.get(key);
            // Удаляем пустые строки
            value = value.replaceAll("(?m)^[ \t]*\r?\n", "");

            // Разделяем строки по числам и сортируем по числам
            String[] lines = value.split("\n");
            TreeMap<Integer, String> sortedLines = new TreeMap<>();
            for (String line : lines) {
                int number = Integer.parseInt(line.split("\\)")[0].replaceAll("[^0-9]", ""));
                sortedLines.put(number, line);
            }

            // Формируем новое значение
            StringBuilder newValue = new StringBuilder();
            int count = 1;
            for (String line : sortedLines.values()) {
                newValue.append(count).append(")").append(line.split("\\)")[1]).append("\n");
                count++;
            }
            map.put(key, newValue.toString().trim());
        }
    }

    /**
     * Полностью очищает корзину
     * @param basket корзина
     * @return уведомление о том, что корзина очищена.
     */
    public String deleteAllPartsFromBasket(BasketServiceImpl basket) {
        basket.contentsBasket.clear();
        return "Корзина очищена";
    }

    /**
     * Этот метод нужен для того, чтобы мы могли заказать запчасти для одного автомобиля, затем для второго
     * и когда вернёмся к первому и начнём снова заказывать наша корзина будет правильно считать последовательность
     * с нужного нам числа
     * @param basket корзина, в которой мы храним товары
     * @param key Машина, к выбору товаров для которой мы вернулись
     * @return Число, которое показывает, какая по счёту будет следующая запчасть для выбранной машины
     */
    public int getSpartCount(BasketServiceImpl basket, String key) {
        int number = 0;
        if (basket.contentsBasket.isEmpty() || !basket.contentsBasket.containsKey(key)){
            return 1;
        }
        String basketString = basket.contentsBasket.get(key);

        // Разбиваем строку на подстроки по символу ") "
        String[] parts = basketString.split("\\) ");

        // Выбираем последнюю подстроку
        String lastPart = parts[parts.length - 1];

        // Извлекаем цифру из последней подстроки
        String[] lastPartParts = lastPart.split("\\) ");
        String lastNumber = lastPartParts[0];
        String[] words = lastNumber.split("\\D+"); // Разделяем строку по нечисловым символам
        for (String word : words) {
            if (!word.isEmpty()) {
                number = Integer.parseInt(word); // Преобразуем строку в число
                break;
            }
        }
        return number + 1;
    }
}
