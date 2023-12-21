package com.urfu.bot;

import com.urfu.domain.basket.Basket;
import com.urfu.domain.car.Car;
import com.urfu.domain.message.MessageToUser;
import com.urfu.services.CarService;
import com.urfu.services.SparePartService;


import static com.urfu.bot.Constants.*;

/**
 * Класс, содержащий все используемые команды для бота.
 */
public class BotCommands {

    /**
     * Добавляет в сообщение наименования машин, на которые имеются комплектующие,
     * а также добавляет соответствующие кнопки с наименованием машиин.
     */
    public MessageToUser setNamesButtonsAndSetTextNamesOfShop(MessageToUser messageToUser, CarService carService){
        MessageToUser resultMessageToUser = messageToUser.clone();
        String answer = "В наличии комплектующие для автомобилей:\n"
                + carService.getNamesCars(", ");
        resultMessageToUser.setButtonNamesSeparatedBySpaces(carService.getNamesCars(" "));
        resultMessageToUser.setText(answer);
        return resultMessageToUser;
    }

    /**
     * Добавляет в текст и в кнопки сообщение об запчастях на выбранный автомобиль.
     */
    public MessageToUser setNamesButtonsAndSetTextNamesOfCars(MessageToUser messageToUser, Car car, SparePartService sparePartService){
        MessageToUser resultMessageToUser = messageToUser.clone();
        String answer = car.getAvailabilityParts();
        String modifiedString = String.join(", ", answer.split(" "));
        resultMessageToUser.setButtonNamesSeparatedBySpaces(sparePartService.getParts(car));
        resultMessageToUser.setText("В наличии комплектующие: " + modifiedString);
        return resultMessageToUser;
    }

    /**
     * Записывает в ответ приветствие и список возможных команд, передаёт этот ответ в сообщение.
     */
    public MessageToUser startCommandReceived(MessageToUser messageToUser, String name){
        MessageToUser resultMessageToUser = messageToUser.clone();
        String answer = "Привет, " + name + ", Это телеграмм бот магазина  автозапчастей." +
                TEXT_START;
        resultMessageToUser.setRemoveKeyboard(true);
        resultMessageToUser.setText(answer);
        return resultMessageToUser;
    }

    /**
     * Изменяет текст переданного сообщения на описание доступных команд бота
     */
    public MessageToUser setTextHelp(MessageToUser messageToUser){
        MessageToUser resultMessageToUser = messageToUser.clone();
        resultMessageToUser.setText(HELP);
        return resultMessageToUser;
    }

    /**
     * Удаляет кнопки бота
     */
    public MessageToUser deleteButtons(MessageToUser messageToUser){
        MessageToUser resultMessageToUser = messageToUser.clone();
        resultMessageToUser.setRemoveKeyboard(true);
        resultMessageToUser.setText("Вы закрыли каталог товаров");
        return resultMessageToUser;
    }

    /**
     * Изменяет текст переданного сообщения на "Команда не найдена"
     */
    public MessageToUser setTextCommandNotFound(MessageToUser messageToUser){
        MessageToUser resultMessageToUser = messageToUser.clone();
        resultMessageToUser.setText("Команда не найдена");
        return resultMessageToUser;
    }
}
