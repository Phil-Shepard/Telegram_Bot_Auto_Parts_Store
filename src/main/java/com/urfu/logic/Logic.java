package com.urfu.logic;

import com.urfu.bot.MessageToUser;
import com.urfu.domain.car.Car;
import com.urfu.services.CarService;
import com.urfu.storage.Storage;

import java.util.List;

/**
 * Класс логики, содержащий все используемые методы
 */
public class Logic {
    /**
     * Добавляет в сообщение наименования машин, на которые имеются комплектующие,
     * а также лдобавляет соответствующие кнопки с наименованием машиин.
     */
    public void setNamesButtonsAndSetTextNamesOfShop(MessageToUser messageToUser, CarService carService) {
        String answer = "В наличии комплектующие для автомобилей:\n"
                + carService.getNamesCars();
        messageToUser.setButtonNamesSeparatedBySpaces(getNameCars());
        messageToUser.setText(answer);
    }

    /**
     * Добавляет в текст и в кнопки сообщение об запчастях на выбранный автомобиль.
     */
    public void setNamesButtonsAndSetTextNamesOfCars(MessageToUser messageToUser, Car car) {
        String answer = car.getAvailabilityParts();
        messageToUser.setButtonNamesSeparatedBySpaces(getParts(car));
        messageToUser.setText(answer);
    }

    /**
     * Записывает в ответ приветствие и список возможных команд, передаёт этот ответ в сообщение.
     */
    public void startCommandReceived(MessageToUser messageToUser, String name) {
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
        messageToUser.setRemoveKeyboard(true);
        messageToUser.setText(answer);
    }

    /**
     * Возвращает все названия машин, на которые есть запчасти.
     */
    public String getNameCars() {
        Storage storage = new Storage();
        List<Car> listCars = storage.getStorage();
        StringBuilder nameCars = new StringBuilder();
        for (Car car : listCars) {
            nameCars.append(car.getName()).append(" ");
        }
        return nameCars.toString();
    }

    /**
     * Возвращает список имеющихся запчастей на конкретную машину в виде строки.
     */
    public String getParts(Car car) {
        return car.getAvailabilityParts();
    }
}
