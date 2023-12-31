package com.urfu.bot;

/**
 * Класс для текстовых констант
 */
public class Constants {
    public static final String COMMAND_START = "/start";
    public static final String COMMAND_EXIT = "/exit";
    public static final String COMMAND_SHOP = "/shop";
    public static final String COMMAND_HELP = "/help";
    public static final String HELP = """
            Справка о доступных командах:
            /shop
            /basket
            /order
            /history
            /delete
            /exit
            /help""";
    public static final String TEXT_START = """
            Доступны следующие команды:
            /shop – Перейти в каталог запчастей.
            /basket - Вывести содержимое корзины.
            /order - Оформить заказ.
            /history - Вывести историю заказов.
            /delete - Удалить все содержимое корзины.
            /exit - Выйти из каталога запчастей.
            /help - Справка.
            """;
}
