package com.urfu.bot.telegram;

import com.urfu.bot.storage.Storage;
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

public class Logic extends TelegramLongPollingBot {
    private BotConfig config;

    public Logic(BotConfig config){
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Это телеграмм бот магазина автозапчастей." +
                "Доступны следующие команды:\n" +
                "/shop – Перейти в каталог запчастей.\n" +
                "/help - Справка\n"));
        listOfCommands.add(new BotCommand("/shop","Перейти в каталог запчастей"));
        listOfCommands.add(new BotCommand("/add","Добавить интересующий вас товар в корзину покупок"));
        listOfCommands.add(new BotCommand("/basket","Перейти в корзину покупок"));
        listOfCommands.add(new BotCommand("/order_report","Сформировать отчёт о самых часто заказываемых товарах"));
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
        Storage storage = new Storage();
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "BMW":
                    takeCarParts(chatId, storage.getCarPatrs("BMW"));
                    break;
                case "Reno":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "Lada":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    sendMessage(chatId, "Справка о дуступных командах:\n" +
                            "/shop\n" +
                            "/add\n" +
                            "/basket\n" +
                            "/order_report\n" +
                            "/help", "cars", null);
                    break;
                default:
                    sendMessage(chatId, "Простите, пока что недоступно", null, null);
            }
        }
    }

    private void takeCarParts(long chatId, Car car){
        String answer = car.getAvailabilityParts();
        sendMessage(chatId, answer, "parts", car);
    }

    private void startCommandReceived(long chatId, String name)  {
        String answer = "Привет, " + name + ", Это телеграмм бот магазина  автозапчастей." +
                " Доступны следующие команды:\n" +
                "/shop – Перейти в каталог запчастей.\n" +
                "/help - Справка\n";
        sendMessage(chatId, answer, "cars", null);
    }

    private void sendMessage(long chatId, String textToSend, String carOrParts, Car car) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        if (carOrParts == "cars")
            message.setReplyMarkup(getKeyBoard(getNameCars()));
        if (carOrParts == "pats")
            message.setReplyMarkup(getKeyBoard(getParts(car)));
        try {
            execute(message);
        }
        catch (TelegramApiException e) {
            System.out.println(e);
        }
    }

    private ReplyKeyboardMarkup getKeyBoard(String listCarsOrParts){
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

    private String getNameCars(){
//        Logic logic = new Logic();
        Storage storage = new Storage();
        List<Car> listCars = storage.getStorage();
        String nameCars = "";
        for (Car car: listCars
        ) {
            nameCars += car.getName()+ " ";
        }
        return nameCars;
    }

    private String getParts(Car car){
        String parts = car.getAvailabilityParts();
        return parts;
    }
}
