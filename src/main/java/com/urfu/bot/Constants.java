package com.urfu.bot;

/**
 * Класс для текстовых констант
 */
public class Constants {
    public static final String COMMAND_START = "/start";
    public static final String COMMAND_EXIT = "/exit";
    public static final String COMMAND_SHOP = "/shop";
    public static final String COMMAND_BASKET = "/basket";
    public static final String COMMAND_ORDER = "/order";
    public static final String COMMAND_HISTORY = "/orderHistory";
    public static final String COMMAND_DELETE = "/delete";
    public static final String COMMAND_WHEELS = "колёса";
    public static final String COMMAND_WHIPERS = "дворники";
    public static final String COMMAND_HEADLIGHTS = "фары";
    public static final String COMMAND_HELP = "/help";
    public static final String COMMAND_COUNT = "/count";
    public static final String HELP = """
            Справка о доступных командах:
            /shop
            /basket
            /order
            /orderHistory
            /delete
            /exit
            /help""";
    public static final String TEXT_START = """
            Доступны следующие команды:
            /shop – Перейти в каталог запчастей.
            /basket - Вывести содержимое корзины.
            /order - Оформить заказ.
            /orderHistory - Вывести историю заказов.
            /delete - Удалить все содержимое корзины.
            /exit - Выйти из каталога запчастей.
            /help - Справка.
            """;
}
