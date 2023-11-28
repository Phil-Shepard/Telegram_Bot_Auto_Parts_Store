package com.urfu.bot.commands;

import com.urfu.bot.services.car.CarServiceImpl;
import com.urfu.bot.storage.Storage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import com.urfu.bot.domain.car.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, содержащий все используемые методы(команды)
 */
public class Commands{
    /**
     * Выводит сообщением наименования машин, на которые имеются комплектующие,
     *  а также выводит соответствующие кнопки с наименованием машиин.
     * @param message
     * @param carService
     */
    public void GetShop(SendMessage message, CarServiceImpl carService){
        String answer = "В наличии комплектующиие для автомобилей: \n" + carService.GetNamesCars();
        message.setReplyMarkup(getKeyBoard(getNameCars()));
        message.setText(answer);
    }

    /**
     * Выводит сообщением и в ввиде кнопок наличие запчастей на выбранный зараее автомобиль.
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
     * @param message
     * @param name
     */
    public void startCommandReceived(SendMessage message, String name)  {
        String answer = "Привет, " + name +  ", Это телеграмм бот магазина  автозапчастей." +
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
     * @return
     */
    public ReplyKeyboardRemove removeKeyboard() {
        ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
        keyboardMarkup.setRemoveKeyboard(true);
        return keyboardMarkup;
    }

    /**
     * Формирует и выводит кнопки.
     * @param listCarsOrParts
     * @return
     */
    public ReplyKeyboardMarkup getKeyBoard(String listCarsOrParts){
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
     * @return
     */
    public String getNameCars(){
        Storage storage = new Storage();
        List<Car> listCars = storage.getStorage();
        String nameCars = "";
        for (Car car: listCars
        ) {
            nameCars += car.getName()+ " ";
        }
        return nameCars;
    }

    /**
     * Возвращает список имеющихся запчастей на конкретную машину в виде строки.
     * @param car
     * @return
     */
    public String getParts(Car car){
        String parts = car.getAvailabilityParts();
        return parts;
    }
}
