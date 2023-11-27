package com.urfu.bot.telegram;

import com.urfu.bot.services.car.CarServiceImpl;
import com.urfu.bot.storage.Storage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.urfu.bot.domain.car.Car;

import java.util.ArrayList;
import java.util.List;

public class Logic extends TelegramLongPollingBot {
    public BotConfig config;

    public Logic(BotConfig config){
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Это телеграмм бот магазина автозапчастей."));
        listOfCommands.add(new BotCommand("/shop","Перейти в каталог запчастей"));
        listOfCommands.add(new BotCommand("/add","Добавить в корзину выбранную запчасть"));
        listOfCommands.add(new BotCommand("/basket","Вывести содержимое корзины"));
        listOfCommands.add(new BotCommand("/order","Оформить заказ"));
        listOfCommands.add(new BotCommand("/history","Вывести историю заказов"));
        listOfCommands.add(new BotCommand("/delete","Удалить из корзины выбранные комплектующие"));
        listOfCommands.add(new BotCommand("/exit","Выйти из каталога запчастей"));
        listOfCommands.add(new BotCommand("/help","Справка"));
        try{
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e){
            System.out.println(e);
        }
    }
    @Override
    public String getBotUsername() { return config.getBotName(); }

    @Override
    public String getBotToken() {return config.getBotToken(); }

    @Override
    public void onUpdateReceived(Update update) {
        CarServiceImpl carService = new CarServiceImpl();
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/exit":
                    sendMessage(chatId, "Вы закрыли каталог товаров", "delete", null);
                    break;
                case "/shop":
                    GetShop(chatId, carService);
                    break;
                case "BMW":
                    takeCarParts(chatId, carService.getCar("BMW"));
                    break;
                case "Renault":
                    takeCarParts(chatId, carService.getCar("Renault"));
                    break;
                case "Lada":
                    takeCarParts(chatId, carService.getCar("Lada"));
                    break;
                case "/help":
                    sendMessage(chatId, "Справка о дуступных командах:\n" +
                            "/shop\n" +
                            "/add\n" +
                            "/basket\n" +
                            "/order\n" +
                            "/history\n" +
                            "/delete\n" +
                            "/exit\n" +
                            "/help", "delete", null);
                     break;
                default:
                    sendMessage(chatId, "Простите, пока что недоступно", null, null);
            }
        }
    }

    public void GetShop(long chatId, CarServiceImpl carService){
        String answer = "В наличии комплектующиие для автомобилей: \n" + carService.GetNamesCars();
        sendMessage(chatId, answer, "cars", null);
    }
    public void takeCarParts(long chatId, Car car) {
        String answer = car.getAvailabilityParts();
        sendMessage(chatId, answer, "parts", car);
    }

    public void startCommandReceived(long chatId, String name)  {
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
        sendMessage(chatId, answer, "delete", null);
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
    public void sendMessage(long chatId, String textToSend, String carOrParts, Car car) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        if (carOrParts == "cars")
            message.setReplyMarkup(getKeyBoard(getNameCars()));
        if (carOrParts == "parts")
            message.setReplyMarkup(getKeyBoard(getParts(car)));
        if (carOrParts == "delete")
            message.setReplyMarkup(removeKeyboard());
        try {
            execute(message);
        }
        catch (TelegramApiException e) {
            System.out.println(e);
        }
    }
}
