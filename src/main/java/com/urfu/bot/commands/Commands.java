package com.urfu.bot.commands;

import com.urfu.bot.services.car.CarService;
import com.urfu.bot.services.car.CarServiceImpl;
import com.urfu.bot.storage.Storage;
import com.urfu.bot.telegram.BotConfig;
import com.urfu.bot.telegram.Logic;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.urfu.bot.domain.car.Car;

import java.util.ArrayList;
import java.util.List;

public class Commands{
//    public void takeCarParts(long chatId, Car car) {
//        String answer = car.getAvailabilityParts();
//        logic.sendMessage(chatId, answer, "parts", car);
//    }
//
//    public void startCommandReceived(long chatId, String name)  {
//        String answer = "Привет, " + name + ", Это телеграмм бот магазина  автозапчастей." +
//                " Доступны следующие команды:\n" +
//                "/shop – Перейти в каталог запчастей.\n" +
//                "/help - Справка\n";
//        logic.sendMessage(chatId, answer, null, null);
//    }
//
//
//    public ReplyKeyboardMarkup getKeyBoard(String listCarsOrParts){
//        String[] buttons = listCarsOrParts.split(" ");
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> keyboardRows = new ArrayList<>();
//        KeyboardRow row = new KeyboardRow();
//        for (String button : buttons
//        ) {
//            row.add(button);
//        }
//        keyboardRows.add(row);
//        keyboardMarkup.setKeyboard(keyboardRows);
//        return keyboardMarkup;
//    }
//
//    public String getNameCars(){
//        Storage storage = new Storage();
//        List<Car> listCars = storage.getStorage();
//        String nameCars = "";
//        for (Car car: listCars
//        ) {
//            nameCars += car.getName()+ " ";
//        }
//        return nameCars;
//    }
//
//    public String getParts(Car car){
//        String parts = car.getAvailabilityParts();
//        return parts;
//    }
//    public void sendMessage(long chatId, String textToSend, String carOrParts, Car car) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(textToSend);
//        if (carOrParts == "cars")
//            message.setReplyMarkup(getKeyBoard(getNameCars()));
//        if (carOrParts == "parts")
//            message.setReplyMarkup(getKeyBoard(getParts(car)));
//        try {
//            execute(message);
//        }
//        catch (TelegramApiException e) {
//            System.out.println(e);
//        }
//    }
}
