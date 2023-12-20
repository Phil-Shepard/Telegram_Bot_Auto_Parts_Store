package com.urfu.bot;

import com.urfu.domain.basket.Basket;
import com.urfu.domain.car.Car;
import com.urfu.domain.message.MessageToUser;
import com.urfu.domain.sparePart.SparePart;
import com.urfu.services.CarService;
import com.urfu.services.SparePartService;

import java.util.ArrayList;
import java.util.Map;

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
        resultMessageToUser.setButtonNamesSeparatedBySpaces(sparePartService.getParts(car));
        resultMessageToUser.setText(answer);
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

    /**
     * Добавляет выбранную запчасть в корзину.
     * @param carName машина, sparePart запчасть
     * @return заполненную корзину
     */
    public MessageToUser addSparePartInBasket(MessageToUser messageToUser, Basket basket, String carName, SparePart sparePart) {
        MessageToUser resultMessageToUser = messageToUser.clone();
        ArrayList<SparePart> partsList = basket.contentsBasket.getOrDefault(carName, new ArrayList<>());
        partsList.add(sparePart);
        basket.contentsBasket.put(carName, partsList);
        resultMessageToUser.setText("Товар " + sparePart.getName() + " успешно добавлен в корзину.");
        return resultMessageToUser;
    }

    /**
     * Возвращает содержимое корзины
     */
    public MessageToUser getBasket(MessageToUser messageToUser, Basket basket) {
        MessageToUser resultMessageToUser = messageToUser.clone();
        StringBuilder basketContents = new StringBuilder("Содержимое корзины:\n");

        for (Map.Entry<String, ArrayList<SparePart>> entry : basket.contentsBasket.entrySet()) {
            String car = entry.getKey();
            ArrayList<SparePart> spareParts = entry.getValue();

            basketContents.append("Машина: ").append(car).append("\n");
            basketContents.append("Запчасти: ");
            if (spareParts.isEmpty()) {
                basketContents.append("Отсутствуют");
            } else {
                for (SparePart part : spareParts) {
                    basketContents.append(part.getName()).append(", ");
                }
                basketContents.delete(basketContents.length() - 2, basketContents.length()); // Удаляем лишнюю запятую
            }
            basketContents.append("\n\n");
        }

        resultMessageToUser.setText(basketContents.toString());
        return resultMessageToUser;
    }
}
