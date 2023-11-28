package com.urfu.bot.telegram;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для текстовых констант
 */
public class Constants {
    public static final String command_start = "/start";
    public static final String command_exit = "/exit";
    public static final String command_shop = "/shop";
    public static final String command_basket = "/basket";
    public static final String command_add = "/add";
    public static final String command_order = "/order";
    public static final String command_history = "/history";
    public static final String command_delete = "/delete";
    public static final String command_help = "/help";
    public static final String HELP = "Справка о дуступных командах:\n" +
            "/shop\n" +
            "/add\n" +
            "/basket\n" +
            "/order\n" +
            "/history\n" +
            "/delete\n" +
            "/exit\n" +
            "/help";

    /**
     * Возвращает лист команд, которые будут доступны.
     * @return
     */
    public static List<BotCommand> listOfCommands() {
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
        return listOfCommands;
    }

}
