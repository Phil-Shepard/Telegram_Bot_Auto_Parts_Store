package com.urfu.bot.telegram;

import com.urfu.bot.services.car.CarServiceImpl;
import com.urfu.bot.storage.Storage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import com.urfu.bot.domain.car.Car;
import static com.urfu.bot.telegram.Constants.*;

import java.util.ArrayList;
import java.util.List;

public class Logic {
    private final Bot bot;

    public Logic(Bot bot){
        this.bot = bot;
    }

    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            CarServiceImpl carService = new CarServiceImpl();
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            switch (messageText){
                case command_start:
                    startCommandReceived(message, update.getMessage().getChat().getFirstName());
                    break;
                case command_exit:
                    message.setReplyMarkup(removeKeyboard());
                    message.setText("Вы закрыли каталог товаров");
                    bot.sendMessage(message);
                    break;
                case command_shop:
                    GetShop(message, carService);
                    break;
                case "BMW":
                    takeCarParts(message, carService.getCar("BMW"));
                    break;
                case "Renault":
                    takeCarParts(message, carService.getCar("Renault"));
                    break;
                case "Lada":
                    takeCarParts(message, carService.getCar("Lada"));
                    break;
                case command_help:
                    message.setText(HELP);
                    bot.sendMessage(message);
                     break;
                default:
                    message.setText("Команда не найдена");
                    bot.sendMessage(message);
            }
        }
    }

    public void GetShop(SendMessage message, CarServiceImpl carService){
        String answer = "В наличии комплектующиие для автомобилей: \n" + carService.GetNamesCars();
        message.setReplyMarkup(getKeyBoard(getNameCars()));
        message.setText(answer);
        bot.sendMessage(message);
    }
    public void takeCarParts(SendMessage message, Car car) {
        String answer = car.getAvailabilityParts();
        message.setReplyMarkup(getKeyBoard(getParts(car)));
        message.setText(answer);
        bot.sendMessage(message);
    }

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
        bot.sendMessage(message);
    }

    public ReplyKeyboardRemove removeKeyboard() {
        ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
        keyboardMarkup.setRemoveKeyboard(true);
        return keyboardMarkup;
    }

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

    public String getParts(Car car){
        String parts = car.getAvailabilityParts();
        return parts;
    }
}
