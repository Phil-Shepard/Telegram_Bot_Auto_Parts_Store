package telegram.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.bot.Logic;
import telegram.config.BotConfig;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {
    private BotConfig config;

    public TelegramBot(BotConfig config){
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
//            log.error("Error setting bot's command list: " + e.getMessage());
            System.out.println(e);
        }
    }
    @Override
    public String getBotUsername() { return config.getBotName(); }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "Старт":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    sendMessage(chatId, "HELP_TEXT");
                    break;
//                case "/myData":
//                    sendMessage(chatId, "HELP_TEXT");
//                    break;
                default:
                    sendMessage(chatId, "Простите, пока что недоступно");
            }
        }
    }
    private void startCommandReceived(long chatId, String name)  {
        String answer = "Привет, " + name + ", Это телеграмм бот магазина  автозапчастей." +
                " Доступны следующие команды:\n" +
                "/shop – Перейти в каталог запчастей.\n" +
                "/help - Справка\n";
//        log.info("Replide to user:" + name);
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        message.setReplyMarkup(getKeyBoard("BMW Lada Reno"));
        try {
            execute(message);
        }
        catch (TelegramApiException e) {
//            log.error("Error occurred:" + e.getMessage());
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
}
